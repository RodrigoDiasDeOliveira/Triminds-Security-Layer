@PostMapping("/login")
public AuthResponse login(@RequestBody AuthRequest request) {
    return loginUseCase.execute(request.email(), request.password());
}