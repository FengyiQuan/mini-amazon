package org.mini_amazon.controllers;

import java.util.List;

import org.aspectj.weaver.ast.Or;
import org.mini_amazon.models.Order;
import org.mini_amazon.models.User;
import org.mini_amazon.repositories.ItemRepository;
import org.mini_amazon.services.AuthService;
import org.mini_amazon.services.ShoppingCartService;
//import org.mini_amazon.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api")
public class UserController {

  @Resource
  private AuthService authService;
  @Resource
  private ShoppingCartService shoppingCartService;

  public record LoginRequest(String username, String password) {
  }

  public record RegisterRequest(String username, String email, String password, String password2) {
  }

  public record AuthenticationResponse(String token, String error) {
  }

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.authenticate(request));
    // try {
    // User account = userRepository.findById(request.username());
    // boolean authentication = account.verifyPassword(request.password());
    // if (!authentication) {
    // throw new Exception("Wrong password");
    // }
    //
    // Map<String, Object> claims = new HashMap<>();
    // claims.put("user", account);
    // String token = JwtTokenUtil.generateToken(claims);
    // System.out.println("token is " + token);
    // // Create a cookie with the JWT token and add it to the response
    // ResponseCookie cookie = ResponseCookie
    // .from("jwt", token)
    // .httpOnly(true)
    // .path("/")
    // .build();
    // HttpHeaders headers = new HttpHeaders();
    //
    // headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
    //
    // User parsed_User = JwtTokenUtil.getClaimsFromToken(token);
    //
    // System.out.println("parse username:" + parsed_User.getUsername());
    //
    // return ResponseEntity.ok().headers(headers).body("ojbk");
    // } catch (Exception e) {
    // return ResponseEntity.status(404).body(e.getMessage());
    // }
  }

  @GetMapping("/health")
  public ResponseEntity<User> health() {
    // System.out.println("token is " + token);
    User parsed_User = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok().body(parsed_User);
  }

  @GetMapping("/cart")
  public ResponseEntity<List<Order>> cart() {
    List<Order> cart = shoppingCartService.getShoppingCart();
    return ResponseEntity.ok().body(cart);
  }

  public record addToCartRequest(int quantity) {
  }

  @PostMapping("/cart")
  public ResponseEntity<String> cart(@RequestBody addToCartRequest request, @RequestParam int item_id)
      throws Exception {
    try {

      shoppingCartService.addCart(item_id, request.quantity());
      return ResponseEntity.ok().body("niuzie gege");
    } catch (Exception e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }
}
