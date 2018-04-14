/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingjdbc.core.parsing.parser.context.condition;

import com.google.common.base.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Or conditions.
 *
 * @author maxiaoguang
 */
@RequiredArgsConstructor
@Getter
@ToString
public final class OrCondition {
    
    private final List<AndCondition> andConditions = new ArrayList<>();
    
    /**
     * Add condition.
     *
     * @param condition condition
     */
    public void add(final Condition condition) {
        if (andConditions.isEmpty()) {
            andConditions.add(new AndCondition());
        }
        andConditions.get(0).getConditions().add(condition);
    }
    
    /**
     * Find condition via column.
     *
     * @param column column
     * @param index index of and conditions
     * @return found condition
     * @deprecated only test call
     */
    @Deprecated
    public Optional<Condition> find(final Column column, final int index) {
        AndCondition andCondition = andConditions.get(index);
        return null != andCondition ? andCondition.find(column) : Optional.<Condition>absent();
    }
}
