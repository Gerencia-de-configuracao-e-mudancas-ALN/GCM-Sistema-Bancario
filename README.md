# GCM-Sistema-Bancario

Sistema bancário para o projeto da segunda e terceira unidades da matéria de Gerência de Configuração e Mudanças

## Especificação dos requisitos (Funcionalidades)

### Cadastrar Conta

- Solicita um número e cria uma conta com este número e saldo igual a zero

### Consulta Saldo

- Solicita um número de conta e exibe o saldo da conta

### Crédito

- Solicita um número e valor  

- Atualiza a conta informada acrescentando o valor informado ao saldo

### Débito

- Solicita um número e valor  

- Atualiza a conta informada subtraindo o valor informado ao saldo

### Transferência

- Solicita o número de conta de origem, número de conta de destino e valor a ser transferido

- Realiza o débito da conta de origem e o crédito na conta destino

### Requisitos adicionais

- As contas podem ter saldo negativo

- Não existe limite de número de contas que podem ser criadas

- A conta deve ter apenas os atributos número e saldo
