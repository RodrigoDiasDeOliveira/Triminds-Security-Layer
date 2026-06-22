@RestController
@RequestMapping("/identities")
public class IdentityController {

    private final CreateIdentityUseCase createIdentity;

    public IdentityController(CreateIdentityUseCase createIdentity) {
        this.createIdentity = createIdentity;
    }

    @PostMapping
    public String create(@RequestBody CreateRequest req) {
        return createIdentity.execute(req.email(), req.password())
                .getId().toString();
    }

    @GetMapping("/health")
    public String health() {
        return "identity-ok";
    }
}