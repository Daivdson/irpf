

# API irpf
Api de importação e leitura das notas de corretagem.

Ideia para o domínio dos dados extraídos: [https://miro.com/app/board/o9J_loak7CA=/?invite_link_id=785499274109](https://miro.com/app/board/o9J_loak7CA=/?invite_link_id=785499274109)

## Fluxo de importação API

![Fluxo](/docs/fluxo-importacao.svg)

**Método `POST` para importação do arquivo:**
 - tipo arquivo
 - 10Mb tamanho máximo
 - 1 arquivo por vez
 - Retorna url do aquivo e nome do arquivo

**Método `GET` para consultar arquivo importado**
Parâmetro necessário: nome do arquivo
