/*
 * Copyright 2016-2018 shardingsphere.io.
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

package io.shardingsphere.proxy.backend.common.jdbc.execute.stream;

import io.shardingsphere.proxy.backend.common.jdbc.execute.memory.ConnectionStrictlyExecuteEngine;
import io.shardingsphere.proxy.transport.mysql.packet.command.statement.execute.PreparedStatementParameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Connection strictly execute engine for JDBC statement protocol.
 *
 * @author zhaojun
 * @author zhangliang
 */
public final class StatementConnectionStrictlyExecuteEngine extends ConnectionStrictlyExecuteEngine {
    
    private final List<PreparedStatementParameter> preparedStatementParameters;
    
    public StatementConnectionStrictlyExecuteEngine(final List<PreparedStatementParameter> preparedStatementParameters) {
        this.preparedStatementParameters = preparedStatementParameters;
    }
    
    @Override
    protected Statement createStatement(final Connection connection, final String sql, final boolean isReturnGeneratedKeys) throws SQLException {
        PreparedStatement result = isReturnGeneratedKeys ? connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : connection.prepareStatement(sql);
        for (int i = 0; i < preparedStatementParameters.size(); i++) {
            result.setObject(i + 1, preparedStatementParameters.get(i).getValue());
        }
        return result;
    }
    
    @Override
    protected boolean executeSQL(final Statement statement, final String sql, final boolean isReturnGeneratedKeys) throws SQLException {
        return ((PreparedStatement) statement).execute();
    }
}
