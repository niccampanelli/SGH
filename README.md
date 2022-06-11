<p align="center">  
  <img width="550" src="https://raw.githubusercontent.com/niccampanelli/SGH/main/src/sgh/util/icons/sgh_logo.png"/>
</p>
<h1 align="center">Sistema de Gerenciamento Hospitalar</h1>
<hr>
<h2>Introdução</h2>
<p>O SGH é um sistema desenvolvido sobre a framework Java ANT com o propósito de facilitar o gerenciamento hospitalar e criar um ambiente digital onde os funcionários do estabelecimento podem otimizar suas funções.</p>
<h2>Recursos</h2>
<p>O sistema foi desenvolvido para cumprir regras de negócio essenciais de um ambiente hospitalar tais como:</p>

- Cadastro de administradores/gerentes
- Cadastro de atendentes
- Cadastro de médicos
- Cadastro de pacientes
- Inserção e registro de consultas
- Anexo de exames às consultas
- Gerenciamento de usuários

<h2>Relatórios</h2>

O SGH utiliza da ferramenta [JasperReport](https://community.jaspersoft.com/) para gerar relatórios e atestados dinâmicamente com dados obtidos do banco de dados. Os relatórios também podem ser exportados em formato PDF, JasperXML, XML ou impresso por meio da ferramenta.

<h2>Contribuidores</h2>

Este sistema foi desenvolvido como um trabalho acadêmico na Universidade Anhembi Morumbi. Colaborativamente com:

- [Alexandre Soares](https://github.com/alexandrests)
- [Tiago Massao](https://github.com/Tmassao)

<h2>Como utilizar</h2>

Este sistema foi desenvolvido com a Netbeans IDE. Para utilizar este projeto, clone este repositório e abra-o na sua IDE. Suas dependências estão associadas ao projeto, não é preciso baixá-las. Caso a IDE não encontre-as, abra as propriedades do projeto e então importe as bibliotecas das pastas `/src/sgh/util` e `/src/sgh/util/database`.
O SGH utiliza o MySQL como banco de dados, então para rodar o sistema será necessário que você tenha um servidor MySQL executando. A URL de acesso do banco de dados pode ser modificada no arquivo de conexão `/src/sgh/util/database/ConnectionClass.java`.
O arquivo SQL para que o banco de dados e as tabelas sejam criadas pode ser encontrado no caminho `/src/sgh/util/database`.

<h2>Imagens</h2>

![Tela 10](https://user-images.githubusercontent.com/56810073/173169246-35184d70-ab56-4069-86ba-e0297bb33d6f.png)
![Tela 9](https://user-images.githubusercontent.com/56810073/173169245-669dcac4-70a4-4b57-88e1-33a4ce57a2a7.png)
![Tela 8](https://user-images.githubusercontent.com/56810073/173169240-ec7792da-7c79-48c2-b2aa-120a6a2b96de.png)
![Tela 7](https://user-images.githubusercontent.com/56810073/173169236-dfd8a9e7-84b5-4539-8ed1-c7e8408b57ff.png)
![Tela 6](https://user-images.githubusercontent.com/56810073/173169234-6f5d223d-f814-4e8f-889d-59539c3d4069.png)
![Tela 5](https://user-images.githubusercontent.com/56810073/173169229-a79b488b-a74b-4f1b-bf3f-be31b5e1b709.png)
![Tela 4](https://user-images.githubusercontent.com/56810073/173169226-cb5f08a0-3796-4527-a6ee-184ed115cfe7.png)
![Tela 3](https://user-images.githubusercontent.com/56810073/173169221-21d441d7-defb-442a-94ff-92d2a7496883.png)
![Tela 2](https://user-images.githubusercontent.com/56810073/173169215-113f50de-e0b3-4b76-b04f-def35a58b7f4.png)
![Tela 1](https://user-images.githubusercontent.com/56810073/173169206-a80eba5e-ca05-4dc4-9b5f-f416688fb2f9.png)

