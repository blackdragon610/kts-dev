■postgresql-xx.jarについて
　$CATALINA_HOME/common/libへ配置すること
　⇒今回のシステムではContext経由（コンテナ管理されている）で
　　DataSourceを取得しているため、コンテナ（つまりTOMCAT）自身が
　　認識できるCLASSPATHに配置しなくてはならないため。
