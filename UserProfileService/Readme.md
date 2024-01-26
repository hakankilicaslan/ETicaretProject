### MongoDB İşlemleri

Not: Mongo ile işlem yaparken admin kullanıcısı ve admin şifreleri kullanılmamalıdır.
Bu nedenle oluşturulacak her bir DB için yeni bir kullanıcı ve şifre tanımlanmalıdır.
1-Öncelikle DB oluşturacağız. UserProfileDB
2-Üzerinde çalışma yapabilmek için mongoDB Compass üzerinde MONGOSH'ı açıyoruz.(sol altta)
3-"use databaseAdı" şeklinde komut girilerek database üzerinde işlem yapmaya geçilir.
4-Bu DB'yi yönetecek olan bir kullanıcı tanımlayacağız.

db.createUser(
{
    user:"defaultUser",
    pwd:"hakan.456",
    roles: ["readWrite","dbAdmin"]
}
)

db.createUser({user:"defaultUser",pwd:"hakan.456,roles: ["readWrite","dbAdmin"]}) bu şekilde tek satır olarak MONGOSH'a yapıştırıyoruz.
