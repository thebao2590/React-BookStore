package com.baokaka.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.baokaka.api.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
	

	
}
