package com.verifymycoin.VerificationManager.model.entity.image;

public enum CustomTextType {
    title {
        @Override
        public CustomText getText(String text) {
            return CustomText.builder()
                    .text(text)
                    .fontFamily("나눔고딕")
                    .fontColor("#344957")
                    .fontSize(48)
                    .fontStyle(CustomText.FontStyle.BOLD)
                    .build();
        }
    },
    subtitle {
        @Override
        public CustomText getText(String text) {
            return CustomText.builder()
                    .text(text)
                    .fontFamily("나눔고딕")
                    .fontColor("#282359")
                    .fontSize(20)
                    .build();
        }
    },
    content{
        @Override
        public CustomText getText(String text) {
            return CustomText.builder()
                    .text(text)
                    .fontFamily("나눔고딕")
                    .fontColor("#282359")
                    .fontSize(16)
                    .build();
        }
    },
    comment{
        @Override
        public CustomText getText(String text) {
            return CustomText.builder()
                    .text(text)
                    .fontFamily("나눔고딕")
                    .fontColor("#B2ACE6")
                    .fontSize(12)
                    .build();
        }
    };

    abstract public CustomText getText(String text);
}