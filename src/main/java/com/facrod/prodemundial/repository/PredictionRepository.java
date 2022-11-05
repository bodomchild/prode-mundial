package com.facrod.prodemundial.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.facrod.prodemundial.entity.Prediction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PredictionRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public Prediction getByUsernameAndMatchId(String username, Integer matchId) {
        return dynamoDBMapper.load(Prediction.class, username, matchId);
    }

    public List<Prediction> getByUsername(String username) {
        var usernameCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(username));
        return dynamoDBMapper.scan(Prediction.class,
                new DynamoDBScanExpression()
                        .withFilterConditionEntry("username", usernameCondition));
    }

}
