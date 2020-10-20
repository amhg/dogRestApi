package com.udacity.DogRestApi.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.udacity.DogRestApi.entity.Dog;
import com.udacity.DogRestApi.repository.DogRepository;
import com.udacity.DogRestApi.exception.DogNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver{

  @Autowired
  private DogRepository dogRepository;

  public Iterable<Dog> findAllDogs(){
    return dogRepository.findAll();
  }

  public Dog findDogById(Long id) {
    Optional<Dog> optionalDog = dogRepository.findById(id);
    if (optionalDog.isPresent()) {
      return optionalDog.get();
    } else {
      throw new DogNotFoundException("Dog not found ", id);
    }

  }

}
