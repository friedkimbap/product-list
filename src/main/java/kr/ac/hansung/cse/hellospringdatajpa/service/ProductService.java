package kr.ac.hansung.cse.hellospringdatajpa.service;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product get(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
    }

    public List<Product> listAll() {
        return repo.findAll();
    }

    public void save(Product product) {
        repo.save(product);
    }


    public boolean checkProductName(Product product) {
      return !product.getName().isEmpty();
    }

    public boolean checkBrand(Product product) {
        return !product.getBrand().isEmpty();
    }

    public boolean checkMadeIn(Product product) {
        return !product.getMadeIn().isEmpty();
    }

    public boolean checkPrice(Product product) {
        return (product.getPrice() <= 0);
    }

    public boolean isExistedProduct(Product product) {
        if(product.getId() == null) {
          return repo.findByNameAndBrand(product.getName(),
              product.getBrand()).isEmpty();
        } else return false;
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}