# Documentação utilizada para desenvolvimento
{
   "info":{
      "title":"API de Impostos do Brasil",
      "description":"API para registrar e calcular os diferentes tipos de impostos no Brasil, como IRPF, ICMS, ISS, entre outros.",
      "version":"1.0.0"
   },
   "basePath":"/api/impostos",
   "endpoints":[
      {
         "path":"/tipos",
         "method":"GET",
         "summary":"Listar todos os tipos de impostos disponíveis",
         "responses":{
            "200":{
               "description":"Lista de tipos de impostos",
               "content":{
                  "application/json":{
                     "example":[
                        {
                           "id":1,
                           "nome":"ICMS",
                           "descricao":"Imposto sobre Circulação de Mercadorias e Serviços",
                           "aliquota":18.0
                        },
                        {
                           "id":2,
                           "nome":"ISS",
                           "descricao":"Imposto sobre Serviços",
                           "aliquota":5.0
                        }
                     ]
                  }
               }
            }
         }
      },
      {
         "path":"/tipos",
         "method":"POST",
         "summary":"Cadastrar um novo tipo de imposto",
         "requestBody":{
            "description":"Dados do novo imposto",
            "required":true,
            "content":{
               "application/json":{
                  "example":{
                     "nome":"IPI",
                     "descricao":"Imposto sobre Produtos Industrializados",
                     "aliquota":12.0
                  }
               }
            }
         },
         "responses":{
            "201":{
               "description":"Imposto criado com sucesso",
               "content":{
                  "application/json":{
                     "example":{
                        "id":3,
                        "nome":"IPI",
                        "descricao":"Imposto sobre Produtos Industrializados",
                        "aliquota":12.0
                     }
                  }
               }
            },
            "400":{
               "description":"Erro de validação nos dados enviados"
            }
         }
      },
      {
         "path":"/calculo",
         "method":"POST",
         "summary":"Calcular um imposto com base no tipo e valor",
         "requestBody":{
            "description":"Dados para cálculo do imposto",
            "required":true,
            "content":{
               "application/json":{
                  "example":{
                     "tipoImpostoId":1,
                     "valorBase":1000.0
                  }
               }
            }
         },
         "responses":{
            "200":{
               "description":"Cálculo do imposto realizado com sucesso",
               "content":{
                  "application/json":{
                     "example":{
                        "tipoImposto":"ICMS",
                        "valorBase":1000.0,
                        "aliquota":18.0,
                        "valorImposto":180.0
                     }
                  }
               }
            },
            "404":{
               "description":"Tipo de imposto não encontrado"
            }
         }
      },
      {
         "path":"/tipos/{id}",
         "method":"GET",
         "summary":"Obter detalhes de um tipo de imposto específico",
         "parameters":[
            {
               "name":"id",
               "in":"path",
               "required":true,
               "description":"ID do tipo de imposto",
               "example":1
            }
         ],
         "responses":{
            "200":{
               "description":"Detalhes do tipo de imposto",
               "content":{
                  "application/json":{
                     "example":{
                        "id":1,
                        "nome":"ICMS",
                        "descricao":"Imposto sobre Circulação de Mercadorias e Serviços",
                        "aliquota":18.0
                     }
                  }
               }
            },
            "404":{
               "description":"Tipo de imposto não encontrado"
            }
         }
      },
      {
         "path":"/tipos/{id}",
         "method":"DELETE",
         "summary":"Excluir um tipo de imposto",
         "parameters":[
            {
               "name":"id",
               "in":"path",
               "required":true,
               "description":"ID do tipo de imposto",
               "example":1
            }
         ],
         "responses":{
            "204":{
               "description":"Tipo de imposto excluído com sucesso"
            },
            "404":{
               "description":"Tipo de imposto não encontrado"
            }
         }
      }
   ]
}


Descrição dos Endpoints
-----------
GET /tipos
Retorna a lista de todos os tipos de impostos cadastrados.

POST /tipos
Cadastra um novo tipo de imposto.
Entrada: nome, descricao, aliquota.
Saída: Dados do imposto criado.

POST /calculo
Calcula o valor do imposto com base no ID do tipo de imposto e no valor base.
Entrada: tipoImpostoId, valorBase.
Saída: tipoImposto, valorBase, aliquota, valorImposto.

GET /tipos/{id}
Retorna os detalhes de um tipo de imposto específico.
Entrada: ID do imposto.
Saída: Dados do imposto.

DELETE /tipos/{id}
Exclui um tipo de imposto pelo ID.

Observações
---
Formatação do JSON: O contrato segue o padrão REST com respostas no formato JSON.

Códigos HTTP:

200: Sucesso na operação.

201: Recurso criado.

204: Recurso excluído.

400: Dados inválidos.

404: Recurso não encontrado.

Escalabilidade: Este modelo permite adicionar novos tipos de impostos e lógica de cálculo facilmente.

