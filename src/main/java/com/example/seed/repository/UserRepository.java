package com.example.seed.repository;

import com.example.seed.vo.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserVo, Long> {
    public List<UserVo> findByUsrId(String usrId);

    public List<UserVo> findByUsrName(String usrName);

    public List<UserVo> findByUsrNameLike(String keyword);
}
