# pathtools とは

eclipse 上でパス操作に関する、便利機能をいくつか提供するプラグインです。類似機能を提供するプラグインはありますが、自分の使い方にちょうど良い物が欲しかったので作りました。

1. パッケージ・エクスプローラー上でフォルダやファイルを選択し、コンテキストメニューから「フォルダを開く」や「コンテキストメニューで開く」を選択すると、それぞれエクスプローラーやコマンドプロンプトでその場所を直接開くことができます。
1. eclipseの「ファイル」メニューに「ワークスペースを開く」メニューを追加します。現在のワークスペースフォルダをエクスプローラーで直接開くことができます。
1. パッケージ・エクスプローラー上でフォルダやファイルを選択し、コンテキストメニューから[Pathtools]メニューのサブメニューを選択すると、そのファイルのパスを様々な形式でクリップボードにコピーすることができます。
	1. ワークスペースからの相対パス
	1. プロジェクトからの相対パス
	1. 修飾名(FQCN)
	1. 名前
	1. 絶対パス
1. ツールバーにフォントサイズ変更用のボタンが追加されます。エディタのフォントサイズをその場ですぐに変更することができます。

# インストール方法

1. [リリースページ](https://github.com/little-forest/pathtools/tree/master/releases)からzipファイルをダウンロードし、展開してください。
1. 展開してできた `plugins` ディレクトリに内にある `jp.littleforest.pathtools_x.y.z.jar` を、eclipse の `plugins` ディレクトリ配下にコピーしてください。
1. elipse を再起動すれば、インストール完了です。


# スクリーンショット

![コンテキストメニュー](https://raw.githubusercontent.com/little-forest/pathtools/images/images/pathtools.png "コンテキストメニュー")

![設定ダイアログ](https://raw.githubusercontent.com/little-forest/pathtools/images/images/preference.png "設定ダイアログ")

![ツールバー](https://raw.githubusercontent.com/little-forest/pathtools/images/images/toolbar.png "ツールバー")

