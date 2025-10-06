package Postagens.Postagens.Repository;



import org.springframework.data.mongodb.repository.MongoRepository;


import Postagens.Postagens.Model.Postagens;

public interface PostagensRepository extends MongoRepository<Postagens, Long> {
    Postagens findByUserId(Long userId);
}
