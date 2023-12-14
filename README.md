# Transactions 
POC written in java 21 spring-boot 


### transaction-bff 
api used for getting transactions from transaction-service
or eventual frontend solutions.

### transaction-worker 
worker used for processing transactions from transaction-bff
and other fronts and store them in data.

## run
```bash
run sudo docker compose up  -d for getting env up