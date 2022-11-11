package com.facrod.prodemundial.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.facrod.prodemundial.entity.dynamodb.Prediction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PredictionRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public Prediction save(Prediction prediction) {
        dynamoDBMapper.save(prediction);
        return prediction;
    }

    public Prediction update(String username, Long matchId, Prediction prediction) {
        var expectedUsernameValue = new ExpectedAttributeValue()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withValue(new AttributeValue().withS(username));
        var expectedMatchIdValue = new ExpectedAttributeValue()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withValue(new AttributeValue().withN(matchId.toString()));
        var saveExpression = new DynamoDBSaveExpression()
                .withExpectedEntry("username", expectedUsernameValue)
                .withExpectedEntry("match_id", expectedMatchIdValue);
        dynamoDBMapper.save(prediction, saveExpression);
        return prediction;
    }

    public List<Prediction> getAllByUsername(String username) {
        var usernameEqCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(username));
        var scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("username", usernameEqCondition);
        return dynamoDBMapper.scan(Prediction.class, scanExpression);
    }

    // Seguramente la use para el calculo de puntos
    public List<Prediction> getAllByMatchId(Long matchId) {
        var matchIdEqCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withN(matchId.toString()));
        var scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("match_id", matchIdEqCondition);
        return dynamoDBMapper.scan(Prediction.class, scanExpression);
    }

    public Prediction getByUsernameAndMatchId(String username, Long matchId) {
        return dynamoDBMapper.load(Prediction.class, username, matchId);
    }

}
