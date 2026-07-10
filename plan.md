# Spring Security Learning Plan

**Learner:** Beginner (no prior Spring Security background)
**Goal:** Deep conceptual mastery + interview readiness
**Style:** Small theory → hands-on practice → interview checkpoint
**Practice project:** This repo (`admin_ecommerce-1`)
**Pace:** ~2–3 hours per module, sequential (each builds on the previous)

---

## How to use this plan

Every module has four parts. Do them in order:

1. **Concept notes (What & Why)** — short read explaining the idea and the problem it solves.
2. **Hands-on task** — a small, concrete change in this project. Write the code, run the app, hit the endpoint.
3. **Interview checkpoint** — 5–8 questions with answers. Say them out loud before reading the answer.
4. **Gotchas** — common bugs and misconceptions you should be able to spot in someone else's code.

Do **not** skip the hands-on step. Reading Spring Security without running it produces false confidence.

---

## Module 1 — Web Security Foundations

Framework-agnostic concepts that Spring Security implements. Skip this and everything downstream feels magical.

**Concepts**
- Authentication vs Authorization (who you are vs what you can do)
- Sessions vs tokens (server-side state vs self-contained credentials)
- Password storage: hashing vs encryption, salt, BCrypt work factor
- CSRF, CORS, XSS — what they are and which layer defends against each
- HTTPS / TLS at a high level: what it protects and what it does not
- Same-origin policy and the browser security model

**Hands-on**
- No code yet. Write a one-page note in your own words explaining: "If I POST a login form from `evil.com` to my `/login`, what stops it?" You should mention CSRF tokens and SameSite cookies.

**Interview checkpoint**
- Difference between hashing and encryption?
- Why is BCrypt preferred over SHA-256 for passwords?
- What is CSRF and why do stateless JWT APIs typically disable it?
- Session fixation — what is it?
- Where should JWTs be stored on the client and what are the trade-offs?

**Gotchas**
- "We hash passwords with SHA-256" — wrong. Fast hashes are attackable.
- Storing JWT in `localStorage` exposes it to XSS.
- Disabling CSRF because "we have JWT" is only correct if you don't use cookies for auth.

---

## Module 2 — Spring Security Architecture

Before writing any config, you must be able to draw the request flow on paper.

**Concepts**
- The Servlet Filter chain and where Spring Security plugs in
- `DelegatingFilterProxy` and the `springSecurityFilterChain` bean
- `SecurityFilterChain` (Spring Security 6+ replaces `WebSecurityConfigurerAdapter`)
- Key filters in order: `SecurityContextHolderFilter`, `UsernamePasswordAuthenticationFilter`, `AuthorizationFilter`, `ExceptionTranslationFilter`
- `SecurityContext` and `SecurityContextHolder` (ThreadLocal)
- The `Authentication` object (principal, credentials, authorities, authenticated flag)
- `GrantedAuthority` vs role string

**Hands-on**
- In `SecurityConfig`, enable DEBUG logging for `org.springframework.security` and print the filter chain on startup. Hit any endpoint and read the log — identify each filter that ran.

**Interview checkpoint**
- Draw the Spring Security filter chain for a JWT-authenticated request.
- Where does the `Authentication` object live during a request?
- What is `DelegatingFilterProxy` and why is it needed?
- What happens if two filters try to authenticate the same request?
- Why did `WebSecurityConfigurerAdapter` get deprecated?

**Gotchas**
- Thinking Spring Security is one filter — it is a chain of ~15.
- Assuming `SecurityContextHolder` is request-scoped — it is `ThreadLocal` and must be cleared.

---

## Module 3 — Core Authentication Components

The classes you will actually implement or configure.

**Concepts**
- `UserDetails` interface — what the framework wants from your user model
- `UserDetailsService` — the "load user by username" contract
- `AuthenticationManager` — orchestrator
- `AuthenticationProvider` — actual authenticator (you can have many)
- `DaoAuthenticationProvider` — the default DB-backed one
- `PasswordEncoder` — plug-in point for hashing

**Hands-on (in this project)**
- Review `user/entity/CustomUserDetails.java` and `user/service/CustomUserDetailsService.java`.
- Confirm `CustomUserDetails` implements `UserDetails` correctly (all seven methods) and returns authorities from the `UserEntity` role.
- Register a `BCryptPasswordEncoder` bean in `SecurityConfig`.
- Signup should hash the password before saving. Verify by inspecting the DB row — you should see `$2a$…`, not plaintext.

**Interview checkpoint**
- What is the contract between `AuthenticationManager` and `AuthenticationProvider`?
- Why is `UserDetailsService` a separate interface from your JPA repository?
- Can you have multiple `AuthenticationProvider`s? Give a real use case.
- What does `DaoAuthenticationProvider` actually do internally?
- Why must `PasswordEncoder` be a bean and not `new BCryptPasswordEncoder()` inline?

**Gotchas**
- Returning `null` from `loadUserByUsername` instead of throwing `UsernameNotFoundException`.
- Comparing raw passwords with `.equals()` anywhere in the code.
- Mixing `getUsername()` (from `UserDetails`) with your own `getEmail()` and getting subtle NPEs.

---

## Module 4 — Your First Real `SecurityConfig`

Wire the pieces together for a form-login app first, so the JWT version in Module 5 makes sense as a contrast.

**Concepts**
- Bean-based configuration with `@Configuration @EnableWebSecurity`
- The `SecurityFilterChain` bean and the lambda DSL
- `authorizeHttpRequests` — permit/require by path
- `formLogin()`, `httpBasic()`, `logout()`
- CSRF: when to keep, when to disable, and why
- Session management modes: `IF_REQUIRED`, `STATELESS`, `NEVER`, `ALWAYS`

**Hands-on**
- In `SecurityConfig`, configure two endpoints:
  - `/auth/**` → `permitAll`
  - Everything else → `authenticated`
- Enable `httpBasic()` temporarily. Use Postman with basic auth on a protected endpoint and confirm it works.
- Then remove `httpBasic()` and switch session management to `STATELESS`. Observe that the same request now fails — this is the setup for JWT.

**Interview checkpoint**
- Difference between `permitAll`, `anonymous`, and `authenticated`?
- When should CSRF be enabled for a Spring app?
- What does `SessionCreationPolicy.STATELESS` actually change?
- What is the order of matching in `authorizeHttpRequests`?
- What happens if no `SecurityFilterChain` bean is defined?

**Gotchas**
- Ordering `.anyRequest().authenticated()` before specific `.requestMatchers(...)` — the specific rules must come first.
- Disabling CSRF globally in a mixed cookie+JWT app.
- Forgetting that `httpBasic()` sends credentials on every request in plaintext-decodable base64.

---

## Module 5 — JWT Stateless Authentication

This maps directly onto your existing `configuring jwt` commit. You will rebuild the JWT layer with full understanding.

**Concepts**
- JWT structure: header, payload, signature
- Signing algorithms: HS256 (symmetric) vs RS256 (asymmetric)
- Claims: `sub`, `iat`, `exp`, `iss`, plus custom claims
- Access token vs refresh token
- `OncePerRequestFilter` — why "once per request"
- Placing your JWT filter *before* `UsernamePasswordAuthenticationFilter`

**Hands-on (rebuild in this project)**
- Recreate `JwtService` with `generateToken`, `extractUsername`, `isTokenValid`. Use `io.jsonwebtoken` (jjwt).
- Recreate `JwtAuthFilter extends OncePerRequestFilter`:
  1. Read `Authorization: Bearer …` header
  2. Extract username, load `UserDetails`
  3. Validate token
  4. Build `UsernamePasswordAuthenticationToken` and set on `SecurityContextHolder`
- Register the filter with `.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)`
- `/auth/login` returns a JWT. `/auth/signup` creates a user. Protected endpoints require the token.

**Interview checkpoint**
- Why does a JWT filter extend `OncePerRequestFilter` and not just `Filter`?
- What is the signature protecting against — tampering or eavesdropping?
- HS256 vs RS256 — when would you choose each?
- Why must the JWT filter run before `UsernamePasswordAuthenticationFilter`?
- If a user's role changes, what happens to their existing JWT? How do you invalidate it?

**Gotchas**
- Using the same secret for signing and encryption.
- Trusting the `alg` header from the token — the `none` algorithm attack.
- Storing sensitive PII in the JWT payload — it is base64, not encrypted.
- Forgetting to set `SecurityContextHolder` clears between requests (it does not automatically for `ThreadLocal` in some containers — use the default strategy).

---

## Module 6 — Authorization Deep Dive

Auth**Z** — controlling what an authenticated user can do.

**Concepts**
- Role vs Authority — `ROLE_` prefix convention
- URL-based authorization with `authorizeHttpRequests` and `hasRole` / `hasAuthority`
- Method security: `@PreAuthorize`, `@PostAuthorize`, `@Secured`, `@RolesAllowed`
- Enable with `@EnableMethodSecurity`
- SpEL in security expressions: `#id`, `authentication.principal`, `hasPermission`
- Domain object security (row-level) — brief overview

**Hands-on**
- Add a `Role` enum to `UserEntity`: `ADMIN`, `STAFF`, `CUSTOMER`.
- URL rules: `/admin/**` → `hasRole("ADMIN")`, `/inventory/**` → `hasAnyRole("ADMIN","STAFF")`, `/customer/**` → `authenticated`.
- Method security on a service method: only the owner of a resource can update it. Use `@PreAuthorize("#customerId == authentication.principal.id")`.
- Test each with three different logged-in users.

**Interview checkpoint**
- Difference between `hasRole("ADMIN")` and `hasAuthority("ADMIN")`?
- `@PreAuthorize` vs `@Secured` — which and why?
- Can you access method arguments in `@PreAuthorize`?
- How does method security work under the hood (AOP)?
- What is domain object security and when do you need it?

**Gotchas**
- Passing `"ROLE_ADMIN"` to `hasRole` — it re-adds the prefix, causing silent failure.
- Method security on a `private` method — AOP proxy cannot intercept.
- Calling a `@PreAuthorize`-annotated method from another method in the same class — proxy is bypassed.

---

## Module 7 — Exception Handling

Auth failures must return sensible JSON to your API clients, not the default Spring HTML error page.

**Concepts**
- `AuthenticationEntryPoint` — triggered when unauthenticated user hits protected resource (401)
- `AccessDeniedHandler` — triggered when authenticated but unauthorized (403)
- `ExceptionTranslationFilter` — the filter that dispatches these
- Coordinating with `@RestControllerAdvice` / your existing `GlobalExceptionHandler`

**Hands-on**
- Implement `RestAuthenticationEntryPoint` — return JSON `{ "status": 401, "error": "Unauthorized", "message": "..." }`.
- Implement `RestAccessDeniedHandler` — return JSON with 403.
- Register both on the `SecurityFilterChain` via `.exceptionHandling(...)`.
- Add cases to `GlobalExceptionHandler` for `BadCredentialsException`, `ExpiredJwtException`, `SignatureException`.

**Interview checkpoint**
- Difference between `AuthenticationEntryPoint` and `AccessDeniedHandler`?
- Why do exceptions thrown in a filter not reach `@RestControllerAdvice` by default?
- How does `ExceptionTranslationFilter` know which handler to invoke?
- Where does `BadCredentialsException` originate?

**Gotchas**
- Assuming `@RestControllerAdvice` catches filter-thrown exceptions — it does not; those are outside the DispatcherServlet.
- Returning stack traces in error responses.

---

## Module 8 — Refresh Tokens & Session Strategy

The step that separates toy JWT tutorials from production auth.

**Concepts**
- Access token (short-lived, ~15 min) vs refresh token (long-lived, ~7–30 days)
- Refresh token rotation and reuse detection
- Storing refresh tokens: DB row with `user_id`, `expiry`, `revoked` flag
- Logout with JWT — server has no session, so either wait for expiry, blacklist, or rotate
- Stateless vs stateful trade-offs

**Hands-on**
- Add a `RefreshToken` entity + repo.
- `/auth/login` returns `{ accessToken, refreshToken }`.
- `/auth/refresh` — accepts a refresh token, validates (exists, not expired, not revoked), issues new access token (and rotates refresh token).
- `/auth/logout` — revokes the refresh token in DB.

**Interview checkpoint**
- Why not just use a long-lived access token?
- What is refresh token rotation and what attack does it prevent?
- How do you log out a user with pure JWT?
- Compare JWT + refresh token vs classic server-side sessions. When would you pick sessions?

**Gotchas**
- Reusing a refresh token multiple times without rotation — one leak permanently compromises the account.
- Storing refresh tokens in `localStorage` — same XSS problem as access tokens.

---

## Module 9 — Advanced / Interview Surface

Breadth topics you should be able to speak to even without deep implementation.

**Concepts**
- OAuth2 roles: resource owner, client, authorization server, resource server
- Authorization Code flow with PKCE — the modern default
- OpenID Connect vs OAuth2 (identity layer vs authorization framework)
- Spring's `spring-security-oauth2-client` and `oauth2-resource-server`
- CORS configuration and where it sits in the chain
- Remember-me tokens
- MFA/2FA concepts (TOTP, WebAuthn) — not implementation
- Rate limiting for auth endpoints
- Common CVEs: JWT `alg=none`, timing attacks, session fixation, open redirect

**Hands-on**
- Configure CORS properly on your `SecurityConfig` for a hypothetical frontend at `http://localhost:5173`.
- Read Spring's OAuth2 client docs and, on paper, sketch how you would add "Login with Google" — you do not need to implement it.

**Interview checkpoint**
- Explain OAuth2 Authorization Code flow with PKCE.
- Difference between OAuth2 and OpenID Connect?
- Where does CORS get evaluated and by whom (browser vs server)?
- How would you rate-limit `/auth/login`?

**Gotchas**
- Confusing OAuth2 (authorization) with authentication.
- Enabling `allowedOrigins("*")` with `allowCredentials(true)` — Spring will reject the config, and correctly so.

---

## Module 10 — Testing Security

You cannot claim to know Spring Security until you can write tests for it.

**Concepts**
- `@WithMockUser`, `@WithUserDetails` — annotations for authenticated tests
- `SecurityMockMvcRequestPostProcessors` — `.with(user(...))`, `.with(jwt())`
- `@SpringBootTest` vs `@WebMvcTest` for security tests
- Testing method security separately from URL security

**Hands-on**
- Write MockMvc tests for:
  - Anonymous user gets 401 from `/inventory/list`
  - `@WithMockUser(roles="CUSTOMER")` gets 403 from `/admin/users`
  - `@WithMockUser(roles="ADMIN")` gets 200 from `/admin/users`
  - A `@PreAuthorize` method throws `AccessDeniedException` for the wrong principal
- Write one full integration test that hits `/auth/login`, extracts the JWT, and uses it to call a protected endpoint.

**Interview checkpoint**
- Difference between `@WithMockUser` and `@WithUserDetails`?
- How do you test a controller method with `@PreAuthorize` without hitting the DB?
- Why does `@WebMvcTest` not enable security by default in some setups?

**Gotchas**
- `@WithMockUser` at the class level being overridden by method-level annotations silently.
- Testing only the happy path — the important tests are the 401 and 403 cases.

---

## Capstone Project

Refactor this repo's auth end-to-end so all ten modules are represented:

- Roles: `ADMIN`, `STAFF`, `CUSTOMER` with correct URL + method security
- JWT with access + refresh tokens, rotation, revocation
- Clean JSON error responses via `AuthenticationEntryPoint` + `AccessDeniedHandler` + `GlobalExceptionHandler`
- BCrypt password hashing
- CORS configured for a real frontend origin
- Full test coverage: 401, 403, 200 for each protected endpoint group, plus a login→protected-call integration test

When this passes, you have working Spring Security knowledge and can defend most of it in an interview.

---

## Reference Reading (as you go)

- Official docs: https://docs.spring.io/spring-security/reference/
- Baeldung Spring Security series (specific articles, not the landing page)
- OWASP Authentication Cheat Sheet
- RFC 7519 (JWT), RFC 6749 (OAuth2), RFC 7636 (PKCE) — skim, do not memorize

---

## Progress Tracker

| Module | Concepts | Hands-on | Interview | Done |
|--------|----------|----------|-----------|------|
| 1. Web security foundations | ☐ | ☐ | ☐ | ☐ |
| 2. Spring Security architecture | ☐ | ☐ | ☐ | ☐ |
| 3. Core auth components | ☐ | ☐ | ☐ | ☐ |
| 4. First `SecurityConfig` | ☐ | ☐ | ☐ | ☐ |
| 5. JWT stateless auth | ☐ | ☐ | ☐ | ☐ |
| 6. Authorization deep dive | ☐ | ☐ | ☐ | ☐ |
| 7. Exception handling | ☐ | ☐ | ☐ | ☐ |
| 8. Refresh tokens | ☐ | ☐ | ☐ | ☐ |
| 9. Advanced / interview surface | ☐ | ☐ | ☐ | ☐ |
| 10. Testing security | ☐ | ☐ | ☐ | ☐ |
| Capstone | — | ☐ | — | ☐ |
