package com.udacity.DogRestApi.mutator;


import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.udacity.DogRestApi.entity.Dog;
import com.udacity.DogRestApi.repository.DogRepository;
import com.udacity.DogRestApi.exception.BreedNotFoundException;
import com.udacity.DogRestApi.exception.DogNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

  @Autowired
  private DogRepository dogRepository;

  public boolean deleteDogBreed(String breed){
    boolean deleted = false;
    Iterable<Dog> dogs = dogRepository.findAll();

    for(Dog d: dogs){
      if(d.getBreed().equals(breed)){
        dogRepository.delete(d);
        deleted = true;
      }
    }
    // Throw an exception if the breed doesn't exist
    if(!deleted){
      throw new BreedNotFoundException("Breed Not Found", breed);
    }
    return deleted;
  }

  public Dog updateDogName(String newDogName, Long id){
    Optional<Dog> optionalDog = dogRepository.findById(id);

    if(optionalDog.isPresent()){
      Dog dog = optionalDog.get();
      dog.setName(newDogName);
      dogRepository.save(dog);
      return dog;
    } else{
      throw new DogNotFoundException("Dog not Found: ", id);
    }
  }

}
