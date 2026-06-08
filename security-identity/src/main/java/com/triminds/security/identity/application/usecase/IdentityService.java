package com.triminds.security.identity.application.usecase;

import com.triminds.security.identity.application.port.out.DomainEventPublisher;
import com.triminds.security.identity.domain.model.*;
import com.triminds.security.identity.domain.repository.IdentityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IdentityService {

    private static final Logger log = LoggerFactory.getLogger(IdentityService.class);
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MINUTES = 30;

    private final IdentityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public IdentityService(IdentityRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Identity createIdentity(String organizationId, String username, String email, String displayName, String type) {
        if (repository.existsByUsername(username)) throw new IdentityAlreadyExistsException("Username já existe: " + username);
        if (repository.existsByEmail(email)) throw new IdentityAlreadyExistsException("Email já cadastrado: " + email);
        IdentityType identityType;
        try { identityType = IdentityType.valueOf(type.toUpperCase()); } catch (Exception e) { identityType = IdentityType.LOCAL_USER; }
        Identity identity = Identity.create(OrganizationId.of(organizationId), username, email, displayName, identityType);
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        log.info("Identidade criada: id={}", saved.getId());
        return saved;
    }

    public Identity activate(String identityId) {
        Identity identity = loadOrThrow(identityId);
        identity.activate();
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }

    public Identity lock(String identityId, String reason, long lockMinutes) {
        Identity identity = loadOrThrow(identityId);
        identity.lock(reason, Instant.now().plus(lockMinutes, ChronoUnit.MINUTES));
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }

    public Identity unlock(String identityId) {
        Identity identity = loadOrThrow(identityId);
        identity.unlock();
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }

    public Identity disable(String identityId, String reason) {
        Identity identity = loadOrThrow(identityId);
        identity.disable(reason);
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }

    public Identity updateProfile(String identityId, String displayName, String email) {
        Identity identity = loadOrThrow(identityId);
        identity.updateProfile(displayName, email);
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }

    public Identity addToGroup(String identityId, String groupId) {
        Identity identity = loadOrThrow(identityId);
        identity.addToGroup(groupId);
        return repository.save(identity);
    }

    public Identity removeFromGroup(String identityId, String groupId) {
        Identity identity = loadOrThrow(identityId);
        identity.removeFromGroup(groupId);
        return repository.save(identity);
    }

    public Identity createFederated(String organizationId, String username, String email, String displayName,
                                    String provider, String externalId, String issuer, String subject) {
        FederationInfo.FederationProvider fp = FederationInfo.FederationProvider.valueOf(provider.toUpperCase());
        Optional<Identity> existing = repository.findByFederation(fp, externalId);
        if (existing.isPresent()) return existing.get();
        String resolvedUsername = repository.existsByUsername(username.toLowerCase())
                ? username.toLowerCase() + "_" + organizationId.substring(0, 8)
                : username.toLowerCase();
        FederationInfo fi = new FederationInfo(fp, externalId, issuer, subject);
        Identity identity = Identity.createFederated(OrganizationId.of(organizationId), resolvedUsername, email, displayName, fi);
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }

    public Identity recordLoginAttempt(String identityId, boolean success) {
        Identity identity = loadOrThrow(identityId);
        if (success) identity.recordSuccessfulLogin();
        else identity.recordFailedLogin(MAX_FAILED_ATTEMPTS, Instant.now().plus(LOCK_DURATION_MINUTES, ChronoUnit.MINUTES));
        Identity saved = repository.save(identity);
        eventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<Identity> findById(String id) { return repository.findById(IdentityId.of(id)); }

    @Transactional(readOnly = true)
    public Optional<Identity> findByUsername(String username) { return repository.findByUsername(username.toLowerCase()); }

    @Transactional(readOnly = true)
    public Optional<Identity> findByEmail(String email) { return repository.findByEmail(email.toLowerCase()); }

    @Transactional(readOnly = true)
    public List<Identity> findByOrganization(String organizationId) { return repository.findByOrganization(OrganizationId.of(organizationId)); }

    @Transactional(readOnly = true)
    public List<Identity> findByOrganizationAndStatus(String organizationId, IdentityStatus status) {
        return repository.findByOrganizationAndStatus(OrganizationId.of(organizationId), status);
    }

    private Identity loadOrThrow(String id) {
        return repository.findById(IdentityId.of(id))
                .orElseThrow(() -> new IdentityNotFoundException("Identidade não encontrada: " + id));
    }
}
