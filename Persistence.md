# Persistence #
## Example ##
Persistence is now working in the TomcatServices branch.

To add a new Company to hsqldb, use for example:
```
EntityManagerFactory emf = Persistence.createEntityManagerFactory("aic.sentiment");
EntityManager manager = emf.createEntityManager();
CompanyEntity c = new CompanyEntity("name", "password", 45.6);

manager.getTransaction().begin();
manager.persist(c);
manager.getTransaction().commit();
```

## Reset HSQLDB ##
To reset HSQLDB so all tables are gone, issue the following SQL query in the HSQL Database Manager: `DROP SCHEMA PUBLIC CASCADE;`