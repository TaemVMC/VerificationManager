//package com.verifymycoin.VerificationManager.model.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.ToString;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Document(collection = "user")
//@Data   // Getter, Setter, toString, default 생성자
//public class User {
//
//    @Id
//    private String id;
//
//    // 1:N 관계
////    @DBRef(db = "verification")
//    private List<Verification> verifications;
//
//}
