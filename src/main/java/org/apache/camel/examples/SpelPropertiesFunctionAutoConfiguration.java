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

import java.util.Optional;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Configuration
@ConditionalOnMissingBean(SpelPropertiesFunction.class)
public class SpelPropertiesFunctionAutoConfiguration {

  @Bean
  @Autowired(required = false)
  public ExpressionParser expressionParser(Optional<SpelParserConfiguration> spelParserConfiguration) {
    ExpressionParser expressionParser;
    if (!spelParserConfiguration.isPresent()) {
      expressionParser = new SpelExpressionParser();
    } else {
      expressionParser = new SpelExpressionParser(spelParserConfiguration.get());
    }
    return expressionParser;
  }
  
  @Bean
  public ParserContext parserContext() {
    ParserContext parserContext = new TemplateParserContext("#[", "]");
    return parserContext;
  }
  
  @Bean
  public StandardEvaluationContext evaluationContext(BeanResolver beanResolver) {
    StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
    evaluationContext.setBeanResolver(beanResolver);
    return evaluationContext;
  }
  
  @Bean
  public BeanFactoryResolver beanResolver(BeanFactory beanFactory) {
    BeanFactoryResolver beanResolver = new BeanFactoryResolver(beanFactory);
    return beanResolver;
  }

  @Bean
  public SpelPropertiesFunction spelPropertiesFunction(SpelExpressionParser expressionParser, ParserContext parserContext, EvaluationContext evaluationContext) {
    SpelPropertiesFunction spelPropertiesFunction = new SpelPropertiesFunction(expressionParser, parserContext, evaluationContext);
    return spelPropertiesFunction;
  }
}
