package com.verifymycoin.VerificationManager.Test;

import com.verifymycoin.VerificationManager.model.entity.User;
import com.verifymycoin.VerificationManager.model.entity.Verification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@Transactional              // transaction을 자동으로 수행해주며, 예외나 에러가 발생하면 test 중 db에 입력된 값들을 rollback 해 줌
@Rollback(value = false)    // 테스트 중에 db에 입력된 값을 테스트 전 원래 상태로 rollback (default: true) -> 나는 값이 들어가는지 확인 위해 false로 지정
public class DBInsertTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    @DisplayName("DB Insert Test")
    public void insertTest() throws ParseException {
        User user = new User();
        String date = "2021-05-05";
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

        Verification verification = Verification.builder()
                        .coinName("ETH")
                        .purchaseAmount("5403601")
                        .startDate(dateParser.parse(date))
                        .build();

        user.setVerifications((List<Verification>) verification);
        mongoTemplate.insert(user);

        Query query = new Query(Criteria.where("coinName").is("ETH"));

        Verification findVerification = mongoTemplate.findOne(query, Verification.class, "verification");

        assertThat(verification.getId(), equalTo(findVerification.getId()));
        assertThat(verification.getCoinName(), equalTo(findVerification.getCoinName()));
        assertThat(verification.getPurchaseAmount(), equalTo(findVerification.getPurchaseAmount()));
        assertThat(verification.getStartDate(), equalTo(findVerification.getStartDate()));
    }
}
