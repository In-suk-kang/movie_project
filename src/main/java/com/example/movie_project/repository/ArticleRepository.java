package com.example.movie_project.repository;
import com.example.movie_project.entity.MemberEntity;
import org.springframework.data.repository.CrudRepository;
//관리대상 entity
public interface ArticleRepository extends CrudRepository<MemberEntity,Long> {

}
