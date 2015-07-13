# UIAutomatorTutorial

UIAutomator é um framework de testes que permite a escrita de testes automatizados para aplicações Android que simulam a interação de um usuário com a UI do sistema.  
O fluxo de uso mais comum é:  
- Encontrar um elemento da UI via id, texto, tipo, etc.
- Interagir com esse elemento (tap, drag, etc).
Pelo fato da interação se dar com os elementos da UI, não é preciso ter o código-fonte do aplicativo sendo testado. É preciso, no entanto, que os elementos tenham alguma característica que permita diferenciá-los dos demais (texto, id, content description, etc).

## *Setup*:
- Baixe e instale o [Android SDK](https://developer.android.com/sdk/index.html)
- Via SDK Manager, instale a _Android Testing Support Library_
- Crie um projeto Java simples, adicionando os seguintes jars ao build path:
  * _android-sdk/platforms/\<api-version\>/android.jar_
  * _android-sdk/platforms/\<api-version\>/uiautomator.jar_
  * _[junit](http://junit.org/)_
- Rode: `android-sdk/tools/android create uitest-project -n <nome_projeto> -t 1 -p <caminho_projeto>`
- Rode: `ant build`
- Rode: `adb push <nome_projeto>.jar /data/local/tmp/`
- Rode: `adb shell uiautomator runtest <nome_projeto>.jar -c <classe_de_teste_totalmente_qualificada>`

## *UIAutomatorViewer*

O UIAutomatorViewer é uma ferramenta fundamental para o desenvolvimento com UIAutomator, visto que ela permite inspecionar os widgets da tela atual (seus IDs, texto, tipos, etc). Com essa informação em mãos, o desenvolvedor pode procurar por eles usando a API do UIAutomator e então simular a interação de um usuário com os mesmos.  
A ferramenta se encontra em _android-sdk/tools/uiautomatorviewer_.

![screenshot do uiautomatorviewer sendo usado no 'Calendar'](https://i.imgur.com/YshxKe5.png)
![screenshot do uiautomatorviewer sendo usado no 'iFood'](https://i.imgur.com/bNSgTVm.png)

## *API*

A documentação oficial da API pode ser encontrada [aqui](https://developer.android.com/reference/android/support/test/uiautomator/package-summary.html).

## *Projeto Exemplo*

O projeto que se encontra nesse repositório se apresenta totalmente comentado, utilizando as principais features da API do UIAutomator.

## *Fontes*:
  - https://developer.android.com/
  - http://www.vogella.com/tutorials/AndroidTesting/article.html
