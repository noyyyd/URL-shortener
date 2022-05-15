package com.altcraft.urlshortener.datalayer.dao;

import com.altcraft.urlshortener.datalayer.LinkInfo;
import com.altcraft.urlshortener.datalayer.connectionpool.ConnectionPool;
import com.altcraft.urlshortener.datalayer.connectionpool.ConnectionPoolException;
import com.altcraft.urlshortener.providers.DBParameters;
import com.altcraft.urlshortener.providers.QueriesProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class MySqlDAO implements DAO {
    private static final Logger logger = Logger.getLogger(MySqlDAO.class);
    private static final String ERROR_CONNECTION = "Connection error";
    private static final String ERROR_DATA = "Data retrieval error";
    private static final int FIRST_ARGUMENT = 1;
    private static final int SECOND_ARGUMENT = 2;
    private static final int THIRD_ARGUMENT = 3;
    private static final int FIRST_COLUMN = 1;
    private final ConnectionPool connectionPool;

    public MySqlDAO() {
        this.connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public void saveLink(LinkInfo linkInfo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(QueriesProvider.get(DBParameters.ADD_LINK));
            preparedStatement.setString(FIRST_ARGUMENT, linkInfo.getOriginalURL());
            preparedStatement.setString(SECOND_ARGUMENT, linkInfo.getShortedURL());
            preparedStatement.setString(THIRD_ARGUMENT, linkInfo.getTimeCreated());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException e) {
            logger.error(ERROR_CONNECTION, e);
        }  catch (SQLException e) {
            logger.error(ERROR_DATA, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public String findHash(String originalLink) {
        String hash = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(QueriesProvider.get(DBParameters.FIND_LINK));
            preparedStatement.setString(FIRST_ARGUMENT, originalLink);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hash = resultSet.getString(FIRST_COLUMN);
            }
        } catch (ConnectionPoolException e) {
            logger.error(ERROR_CONNECTION, e);
        }  catch (SQLException e) {
            logger.error(ERROR_DATA, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }

        return hash;
    }

    @Override
    public String findOriginalLink(String hash) {
        String originalLink = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(QueriesProvider.get(DBParameters.FIND_HASH));
            preparedStatement.setString(FIRST_ARGUMENT, hash);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                originalLink = resultSet.getString(FIRST_COLUMN);
            }
        } catch (ConnectionPoolException e) {
            logger.error(ERROR_CONNECTION, e);
        }  catch (SQLException e) {
            logger.error(ERROR_DATA, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }

        return originalLink;
    }
}
