# Projeto 
Este projeto visa fazer calculos para apresentar o melhor candidato para se fazer o teste baseado no seu desempenho 
 em produçao ou seja quanto maior a porcentagem de erros maior e a probabilidade desse candidato ao teste. Esse serviço
 depende de consumir uma api externa para colher os dados no formato de csv.
 
# Run Mock
Antes de rodar o projeto, rodar esse mock abaixo para simular o serviço de consumo.

subir o containner do mockserver
```
docker run -d --rm -p 1080:1080 mockserver/mockserver
```

Depois de subir o docker rodar o sh dentro da pasta de mockApi para criar os endpoints

```
. mockApi/createEndpoints.sh
```

### Mais informações sobre o mock
https://www.mock-server.com/where/docker.html

#Built with
* [Kotlin](https://kotlinlang.org/) - language
* [SpringBoot](https://spring.io/) - Framework Web
* [Gradle](https://gradle.org/) - More than just a building toolkit    
