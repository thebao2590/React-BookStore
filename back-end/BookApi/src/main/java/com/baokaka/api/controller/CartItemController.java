package com.baokaka.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baokaka.api.model.CartItem;
import com.baokaka.api.payloads.AuthResponse;
import com.baokaka.api.payloads.BookResponse;
import com.baokaka.api.payloads.ResponseCartItem;
import com.baokaka.api.repository.BookRepository;
import com.baokaka.api.repository.CartItemRepository;

@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600)
@RestController
@RequestMapping(path="/api/cartitem")
public class CartItemController {
	@Autowired
	public CartItemRepository cartRepository;
	
	@Autowired
	public BookRepository bookRp;
	
	@GetMapping("/{id}")
	public List<ResponseCartItem> getCartItemByUserId(@PathVariable("id") int id){
		List<ResponseCartItem> list = new ArrayList<ResponseCartItem>();
		for (CartItem cartItem : cartRepository.findAll()) {
			if(cartItem.getUser_id()==id) {	
				list.add(new ResponseCartItem(cartItem.getId(),
						new BookResponse(bookRp.findById(cartItem.getBook_id()).get()),
						cartItem.getUser_id(),cartItem.getQty()));
			}
		}
		return list;
		
	}
	
	@PostMapping("")
	public AuthResponse addCartItem(@RequestBody CartItem item) {
		List<CartItem> list = cartRepository.findAll();
		for(CartItem cart:list) {
			if(item.getBook_id() == cart.getBook_id()&&item.getUser_id()==cart.getUser_id()) {
				int qty = item.getQty() + cart.getQty();
				cart.setQty(qty);
				cartRepository.save(cart);
				return new AuthResponse(true, "???? c???p nh???t th??ng tin gi??? h??ng");
			}
		}
		try {
			cartRepository.save(item);
			return new AuthResponse(true, "???? th??m v??o gi??? h??ng");
		} catch (Exception e) {
			return new AuthResponse(false, "Th??m v??o gi??? h??ng th???t b???i");
		}
		
		
	}
	
	@PutMapping("/{id}")
	public AuthResponse update(@PathVariable("id") int id ,@RequestBody CartItem addr){
		try {
			addr.setId(id);
			cartRepository.save(addr);
			return new AuthResponse(true, "???? c???p nh???t th??ng tin gi??? h??ng");
		}catch (Exception e) {
			return new AuthResponse(false, "C???p nh???t th???t b???i");
		}
	}
	
	@DeleteMapping("/{id}")
	public AuthResponse delete(@PathVariable("id") int id) {
		try {
			cartRepository.deleteById(id);
			return new AuthResponse(true, "X??a th??nh c??ng");
		} catch (Exception e) {
			return new AuthResponse(false, "X??a th???t b???i");
		}
	}
 }
