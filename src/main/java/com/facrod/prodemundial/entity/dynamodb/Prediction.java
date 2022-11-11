package com.facrod.prodemundial.entity.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "test_prediction")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prediction {

    @DynamoDBHashKey
    private String username;

    @DynamoDBRangeKey(attributeName = "match_id")
    private Long matchId;

    @DynamoDBAttribute
    private String winner;

    @DynamoDBAttribute(attributeName = "home_score")
    private int homeScore;

    @DynamoDBAttribute(attributeName = "away_score")
    private int awayScore;

    @DynamoDBAttribute(attributeName = "extra_time")
    private MatchExtra extraTime;

    @DynamoDBAttribute(attributeName = "penalties")
    private MatchExtra penalties;

}
