dataSource {
    pooled = true
    driverClassName = "org.postgresql.Driver"
    dialect = org.hibernate.dialect.PostgreSQLDialect
    username = "VoterContactManager"
    password = "VoterContactManager"
//    properties {
//        maxActive = -1
//        minEvictableIdleTimeMillis=1800000
//        timeBetweenEvictionRunsMillis=1800000
//        numTestsPerEvictionRun=3
//        testOnBorrow=true
//        testWhileIdle=true
//        testOnReturn=true
//        validationQuery="SELECT 1"
//    }
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            // url = "jdbc:mysql://192.168.1.142:3306/VoterContactManager"
            url = "jdbc:postgresql://192.168.1.142:5432/VoterContactManagerDev"
            loggingSql = true
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            // url = "jdbc:mysql://192.168.1.142:3306/VoterContactManager"
            url = "jdbc:postgresql://192.168.1.142:5432/VoterContactManagerDev"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            // url = "jdbc:mysql://192.168.1.142:3306/VoterContactManager"
            url = "jdbc:postgresql://192.168.1.142:5432/VoterContactManager"
            pooled = true
        }
    }
}
