package com.facrod.prodemundial.entity.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.facrod.prodemundial.enums.MatchExtraType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class MatchExtra {

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private MatchExtraType type;

    @DynamoDBAttribute(attributeName = "home_score")
    private int homeScore;

    @DynamoDBAttribute(attributeName = "away_score")
    private int awayScore;

}
