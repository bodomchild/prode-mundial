package com.facrod.prodemundial.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.facrod.prodemundial.entity.dynamodb.MatchPrediction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchPredictionRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public MatchPrediction save(MatchPrediction matchPrediction) {
        dynamoDBMapper.save(matchPrediction);
        return matchPrediction;
    }

    public MatchPrediction update(String username, Long matchId, MatchPrediction matchPrediction) {
        var expectedUsernameValue = new ExpectedAttributeValue()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withValue(new AttributeValue().withS(username));
        var expectedMatchIdValue = new ExpectedAttributeValue()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withValue(new AttributeValue().withN(matchId.toString()));
        var saveExpression = new DynamoDBSaveExpression()
                .withExpectedEntry("username", expectedUsernameValue)
                .withExpectedEntry("match_id", expectedMatchIdValue);
        dynamoDBMapper.save(matchPrediction, saveExpression);
        return matchPrediction;
    }

    public List<MatchPrediction> getAllByUsername(String username) {
        var usernameEqCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(username));
        var scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("username", usernameEqCondition);
        return dynamoDBMapper.scan(MatchPrediction.class, scanExpression);
    }

    // Seguramente la use para el calculo de puntos
    public List<MatchPrediction> getAllByMatchId(Long matchId) {
        var matchIdEqCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withN(matchId.toString()));
        var scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("match_id", matchIdEqCondition);
        return dynamoDBMapper.scan(MatchPrediction.class, scanExpression);
    }

    public MatchPrediction getByUsernameAndMatchId(String username, Long matchId) {
        return dynamoDBMapper.load(MatchPrediction.class, username, matchId);
    }

}
