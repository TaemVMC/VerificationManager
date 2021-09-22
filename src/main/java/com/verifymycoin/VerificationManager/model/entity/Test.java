package com.verifymycoin.VerificationManager.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "test")
@Data
public class Test {

    @Id
    @ApiModelProperty(value = "아이디")
    private String id;

    @ApiModelProperty(value = "제목", example = "제목")
    private String title;

    @ApiModelProperty(value = "이미지URL", example = "asdfa")
    private String image;
}
