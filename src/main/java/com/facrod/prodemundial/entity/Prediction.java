package com.facrod.prodemundial.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "test_prediction")
public class Prediction {

    @DynamoDBHashKey
    private String username;

    @DynamoDBRangeKey(attributeName = "match_id")
    private Integer matchId;

    @DynamoDBAttribute(attributeName = "home_score")
    private Integer homeScore;

    @DynamoDBAttribute(attributeName = "away_score")
    private Integer awayScore;

    @DynamoDBAttribute
    private String winner;

}
