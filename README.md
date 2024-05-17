# Doping Hafıza "Backend Challenge" Projesi
Bu proje, bir öğrenci test servisi oluşturmayı amaçlamaktadır. Servis, test ve öğrencilerin yönetimini, öğrencilerin testlere katılmasını, cevapladıkları soruları ve test performanslarını takip etmeyi sağlar. Ayrıca, çeşitli kurallar ve validasyonlarla verilerin doğruluğunu ve bütünlüğünü sağlar. Proje, Spring Boot 3.2.5 ile Java 17 kullanılarak geliştirilmiştir ve database olarak PostgreSQL tercih edilmiştir.
# Proje Hakkında
Projeyi çalıştırdıktan sonra ilk olarak test oluşturulması gerekmektedir. Yeni bir Test (Exam.class) oluşturulabilmesi için en az bir adet Soru (Question.class) ve o soruya ait en az bir adet Yanıt (QuestionItem.class) oluşturulması gerekmektedir. Daha sonradan teste soru ve yanıt ekleme, silme ya da güncelleme işlemi yapabilirsiniz. Test oluşturulurken teste ve sorularına puan ataması yapmanız gerekemektedir. Test ilk oluşturulduğunda durumu pasif olarak gözükecektir. Teste ait soruların puanlarının toplamı ve testin puanı eşit olduğu zaman testi değerlendirme işlemine katabilmeniz için verify işlemi yapmanız gerekmektedir. Verify olmayan testler öğrencilere atanamaz ve değerlendirme yapılamaz. Sorulara ait yanıtlarda bulunan `isValid` değeri o yanıtın doğru olduğunu gösterir. Yanıtlar eklenirken en az bir tane doğru seçenek eklenmesi zorunlu değildir. Bu işlem kullanıcı tarafına bırakılmıştır.

Test oluşturup verify ettikten sonra bir öğrenci oluşturabilirsiniz. Oluşturduğunuz yeni öğrenciye öncelikle test tanımlaması yapılması gerekmektedir. Tanımlama yapılmadan öğrenci ilgili testi çözemeyecektir. Bu işlemler yapıldıktan sonra öğrenci testi çözebilir ve çözülen testi, almış olduğu puanı ve soru yanıtlarını görebilirsiniz.

# Kurulum
Projeyi çalıştırmak için aşağıdaki adımları takip edebilirsin:
- Proje içerisinde yer alan application.properties içerisinde bulunan database bağlantı bilgilerini düzenleyin.
- Local veri tabanında **"test"** isminde yeni bir database oluşturun.
- Projeyi çalıştırdığınızda veri tabanı tabloları otomatik olarak oluşturulacaktır.

# Request Mapping Açıklamaları
- /v1/api/exams/** : Test, soru ve yanıt oluşturma, güncelleme, silme, ve getirme operasyonlarını yönetir. 
- /v1/api/students/** : Öğrenci oluşturma, güncelleme, silme ve getirme operasyonlarını yönetir.
- /v1/api/exam-scores/** : Değerlendirme oluşturma, silme ve getirme operasyonlarını yönetir.

# Request Modelleri
Bazı örnek requestleri aşağıya ekliyorum. Requeslerde yer alan id datasını responselardan gelen dataya göre düzenlemeniz gerekmektedir.

[Swagger](http://localhost:8080/swagger-ui/index.html) arayüzü üzerinden de request ve response modellerine ulaşabilirsiniz.

## Yeni Test Oluşturma
Test ismi ve puanıyla beraber en az bir adet soru ve o soruya ait en az bir adet yanıt oluşturmamız gerekmektedir.
```
{
    "name": "Türkçe Test",
    "maxScore": 100,
    "questions": [
        {
            "text": "Soru 1",
            "point": 50,
            "items": [
                {
                "tag": "A",
                "text": "Cevap 1",
                "isValid": true
                },
                {
                "tag": "B",
                "text": "Cevap 2",
                "isValid": false
                },
                {
                "tag": "C",
                "text": "Cevap 3",
                "isValid": false
                },
                {
                "tag": "D",
                "text": "Cevap 4",
                "isValid": false
                }
            ]
        },
        {
            "text": "Soru 2",
            "point": 50,
            "items": [
                {
                "tag": "A",
                "text": "Cevap 5",
                "isValid": false
                },
                {
                "tag": "B",
                "text": "Cevap 6",
                "isValid": true
                },
                {
                "tag": "C",
                "text": "Cevap 7",
                "isValid": false
                },
                {
                "tag": "D",
                "text": "Cevap 8",
                "isValid": false
                }
            ]
        }
    ]
}
```
## Test Güncelleme
Request body içerisinde bütün entity datasını gönderebileceğimiz gibi sadece güncellemek istediğimiz dataları da gönderebiliriz.
```
{
    "name": "Matematik Test",
    "maxScore": 25,
    "questions": [
        {
            "id": 1052,
            "point": 10,
            "items": [
                {
                    "id": 1152,
                    "text": "Cevap 2"
                }
            ]
        }
    ]
}
```
## Var Olan Teste Yeni Soru Eklemek
Tek başına soru bir anlam ifade etmediği için soruyla beraber en az bir adet yanıt da eklemeniz gerekemektedir.
```
{
    "text": "Yeni Eklenen Soru 1",
    "point": 10,
    "items": [
        {
            "tag": "Yeni Tag",
            "text": "Yeni Text",
            "isValid": false
        }
    ]
}
```
## Var Olan Teste Ait Soruya Yanıt Eklemek
Aynı anda istenen soruya birden fazla yanıt eklenebilir.
```
[
    {
        "tag": "Yeni Tag 2",
        "text": "Yeni Text 2",
        "isValid": false
    }
]
```
## Var Olan Öğrenciye Test Ataması Yapmak
Öğrenci testi çözdükten sonra responseda `activeExams` listesinde olan `isCompleted` değeri `true` olarak gelecektir.
```
{
    "exams" : [
        {
            "id": 1000
        }
    ]
}
```

## Yeni Test Değerlendirmesi Eklemek
Responseda gelen `answers` listesinde bulunan `valid` değeri sorunun doğru cevaplandığı bilgisini verir.
```
{
    "student": {
        "id": 452,
        "identityNumber": "90106530"
    },
    "exam": {
        "id": 752,
        "answers": [
            {
                "questionId": 1803,
                "answerId": 1986
            },
            {
                "questionId": 1802,
                "answerId": 1952
            }
        ]
    }
}
```
 
