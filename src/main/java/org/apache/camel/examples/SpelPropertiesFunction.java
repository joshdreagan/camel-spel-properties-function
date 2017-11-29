/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.examples;

import org.apache.camel.component.properties.PropertiesFunction;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpelPropertiesFunction implements PropertiesFunction {

  private final SpelExpressionParser expressionParser;
  private final ParserContext parserContext;
  private final EvaluationContext evaluationContext;

  public SpelPropertiesFunction(SpelExpressionParser expressionParser, ParserContext parserContext, EvaluationContext evaluationContext) {
    this.expressionParser = expressionParser;
    this.parserContext = parserContext;
    this.evaluationContext = evaluationContext;
  }
  
  @Override
  public String getName() {
    return "spel";
  }

  public SpelExpressionParser getExpressionParser() {
    return expressionParser;
  }

  public ParserContext getParserContext() {
    return parserContext;
  }

  public EvaluationContext getEvaluationContext() {
    return evaluationContext;
  }

  @Override
  public String apply(String remainder) {
    Expression expression = expressionParser.parseExpression(remainder, parserContext);
    return expression.getValue(evaluationContext, String.class);
  }
}
