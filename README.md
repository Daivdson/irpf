# API irpf
Api de importação e leitura das notas de corretagem.

Ideia para o domínio dos dados extraídos: [https://miro.com/app/board/o9J_loak7CA=/?invite_link_id=785499274109](https://miro.com/app/board/o9J_loak7CA=/?invite_link_id=785499274109)

# Fluxo de importação `nota corretagem` da API


![Fluxo](/docs/fluxo-importacao.svg)

**Método `POST` para importação do arquivo:**
 - tipo arquivo
 - 10Mb tamanho máximo
 - 1 arquivo por vez
 - Retorna chave da importacao

# Relatório de declaração IRRF (Bolsa de valores)
O relatório completo vai contemplar o período de 1 ano. Pode ser gerado alguns relatórios específicos sendo possível passar o período como parâmetro.

## Declaração de ação

**Bens e direitos** 
Deve ser lançado a posição atual da sua carteira, posição atual do dia 31/12/2019(exemplo). Os ativos que você possui atualmente.

- **Ações opção - 31:** Lançar todas as ações. Na descriminação pode colocar “10 ações da Petrobras ao preço médio de R$14,00 total de R$140,00”, pode colocar mais informações caso queira. No campo situação atual colocar total de ações, repete o mesmo valor informado na descriminação, caso haja situação anterior também informa(que seria o valor total dessa ação ano passado) caso haja.

**Rendimentos isentos não tributáveis**
-   **Dividendos opção - 09:** Total recebido no ano de cada empresa, inserir empresa por empresa do total recebido no ano por essa empresa.
    
-   **Bonificação opção - 18:** Total recebido no ano de bonificação pela empresa, o valor a ser colocado é custo por ação.
    
-   **Operações até 20mil… opção- 20:** Colocar o valor total de lucro e prejuízo do ano. Somar todo o lucro e prejuízo do ano e informa, não é necessário nenhuma empresa penas o valor total. 

**Dividas e ônus reais** 
Só é lançando caso você vire o ano vendido em alguma ação, caso contrario não é necessário.

**Rendimentos sujeitos a tributação exclusiva/definitiva**

-   **Juros sobre capital próprio opção 10:** Total recebido no ano de cada empresa, inserir empresa por empresa do total recebido no ano por essa empresa.
    
-   **Aluguel de ações opção - 06:** Total recebido no ano de cada empresa, inserir empresa por empresa do total recebido no ano por essa empresa.

**Renda variável**
- **Operações comuns / day trade**
Se vendeu mais de 20mil em ações no mês, deve ser declarar o lucro para ser taxado. Deve gerar a DARF para ser paga até ultimo dia do mês seguinte.
Se não vendeu mais de 20mil não deve lançar nada, se houver prejuízo no mês deve lançar o prejuízo.
Os valores que devem ser lançado é o valor total de cada mês de lucro/prejuízo.

## Declaração fundos imobiliários
**Rendimentos isentos não tributáveis**
 -   **Dividendos opção - 26:** Total recebido no ano de cada fundo, inserir empresa por empresa do total recebido no ano por essa empresa. Na descrição informa apenas “Proventos recebidos em 2019 do VISC11”.

**Bens e direitos** 
Deve ser lançado a posição atual da sua carteira, posição atual do dia 31/12/2019(exemplo). Os fundos que voçe possui atualmente.

 -   **Fundos de investimento opção - 73:** Lançar todas fundos. Na descriminação informa o fundo e preço. No campo situação atual colocar total do fundo, repete o mesmo valor informado na descriminação, informa valor também na situação do ano anterior caso haja.
    
 -   **Fundos de curto prazo (CP) opção - 71:** Lançar todos fundos. Na descriminação informa o fundo e preço. No campo situação atual colocar total do fundo, repete o mesmo valor informado na descriminação, informa valor também na situação do ano anterior caso haja.
    
 -   **Fundos de longo prazo (LP) opção - 72:** Lançar todos fundos. Na descriminação informa o fundo e preço. No campo situação atual colocar total do fundo, repete o mesmo valor informado na descriminação, informa valor também na situação do ano anterior caso haja.
    
 -   **Fundos de ações (FIA) opção - 74:**  Lançar todos fundos. Na descriminação informa o fundo e preço. No campo situação atual colocar total do fundo, repete o mesmo valor informado na descriminação, informa valor também na situação do ano anterior caso haja.

**Renda variável**

 - **Operações fundos investimento imobiliário**  Todas as operações vendas de fundos devem ser lançadas, ao contrário das ações que são
   isentas até 20mil reais. Então deve ser lançado o valor total da
   operação de lucro/prejuízo (lucro ou prejuízo da operação) total de
   cada mês que teve venda.
   
   **Obs**: Lembrando que deve ser paga uma DARF da operação que foi feita, só depois deve ser declarado, então se você fez operações e
   não pagou até o final do mês subsequente essa DARF. Gere a DARF com a
   respectiva multa(se houver), pague e depois sim faça a declaração.

## Declaração de renda fixa
**Bens e direitos** 
Deve ser lançado a posição atual da sua carteira, posição atual do dia 31/12/2019(exemplo). Os fundos que voçe possui atualmente.

-   **Aplicação renda fixa opção - 45:** Lançar todos os títulos com dados do informe de rendimento e o valor total na situação e o valor total também do ano anterior caso tenha. Na descriminação informar “Aplicação em títulos do tesouro direto Tesouro IPCA+2045 no total de 1000"
    
-   **Deposito bancário conta corrente - 61/69 :** Se houver algum saldo em conta na corretora deve ser informado esse valor.
Na descriminação informar: Saldo na corretora Clear.

  

**Rendimentos isentos não tributáveis**
 Em renda fixa nem todos os títulos são isentos como  LCI, LCA, CRI, CRA verificar se seu tilo se encaixa nesse critério.

-   **Rendimentos opção - 12:** Rendimento do valor aplicado no titulo de cada empresa, inserir cada um dos títulos.

**Rendimentos sujeitos a tributação exclusiva/definitiva**

-   **Rendimentos de aplicação opção 06:** Colocar somente quando você vende e ou o valor de rendimento que teve no ano se houver.



# REST - [Declaração de ação](#declaração-de-ação)

**Relatório completo por ano - `GET	 {index}/relatorio`**
**Query parâmetros:** ano

| Query parâmetro |  |
|--|--|
| ano | Ano para geração de relatório se null considera último ano `ex: ano=2021` |
