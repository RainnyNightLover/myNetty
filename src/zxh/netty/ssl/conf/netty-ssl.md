### jdk  keytools生成ssl证书命令

## 1 生成服务端及客户端证书

#### --生成服务器私钥,存储进到证书仓库

keytool -genkey -alias securechat -keysize 2048 -validity 365 -keyalg RSA -dname "CN=localhost" -keypass sNetty
-storepass sNetty -keystore sChat.jks

#### --生成客户端自签名证书,存储进到证书仓库

keytool -genkey -alias smcc -keysize 2048 -validity 365 -keyalg RSA -dname "CN=localhost" -keypass cNetty -storepass
cNetty -keystore cChat.jks

## 2 将服务端证书导入到客户端证书仓库中

#### --导出服务端的自签名证书

keytool -export -alias securechat -keystore sChat.jks -storepass sNetty -file sChat.cer

#### --将服务端的自签名证书导入到客户端的信任证书仓库中

keytool -import -trustcacerts -alias securechat -file sChat.cer -storepass cNetty -keystore cChat.jks

## 3 将客户端证书导入到服务端证书仓库中

#### --导出客户端的自签名证书

keytool -export -alias smcc -keystore cChat.jks -storepass cNetty -file cChat.cer

#### --将客户端的自签名证书导入到服务端的信任证书仓库

keytool -import -trustcacerts -alias smcc -file cChat.cer -storepass sNetty -keystore sChat.jks  

