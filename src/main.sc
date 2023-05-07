#require: slotfilling/slotFilling.sc
  #module = sys.zb-common
  
require: common.js
  module = sys.zb-common
  
# Подключение javascript обработчиков
#require: js/getters.js

# Подключение сценарных файлов
#require: sc/addNote.sc

require: dicts/answers.yaml
  var = $Answers

patterns:
    $AnyText = $nonEmptyGarbage

theme: /
    state: Start
        q!: * *start
        q!: * {(как*/шаблон*) (звук*) * [есть/имеется/созда*/запусти*]} *
        q!: * (~помощь/~справка/помоги*/help/хелп/~меню/може*/умее*) *
        script:
            $jsapi.startSession();
            $reactions.answer($Answers["Start"]);
        buttons:
            "Начать игру" -> /Пример
            "Выбрать режим" -> /Настройки

    state: Fallback
        event!: noMatch
        a: Я не понимаю

theme: /
           
    state: Настройки
        q!: * (настройк*/настрои*/режим*) *
        q!: выбрать режим
        script: 
            $reactions.answer($Answers["Options"]);
        buttons:
            "Типы операций" -> /ТипОперации
            "Количество операций" -> /КоличествоОпераций
            "Диапазон чисел в примерах" -> /ДиапазонПримера
            "Диапазон чисел в ответах" -> /ДиапазонОтвета
            
    state: ТипОперации
        a: ммм
        #Страничка с галочками: Сложение, Вычитание, Умножение, Деление
        
    state: КоличествоОпераций
        a:  Выбери количество операций в одном примере (от 1 до 40)
        q!: $AnyText::anyText
        
    state: ДиапазонПримера
        a:  Выбери диапазон чисел в примере (Пример: от 1 до 10000), граница: 10000
        q!: $AnyText::anyText
        
    state: ДиапазонОтвета
        a:  Выбери диапазон чисел в ответе (Пример: от 1 до 10000), граница: 10000
        q!: $AnyText::anyText
        
    state: ЗаданиеВыполнено
        event!: done
        event!: DONE
        random: 
            a: Молодец!
            a: Красавичк!
            a: Супер!
            
    state: Пример
        q!: (~дай задачу|~дай пример) 
            [~уравнение|~пример|~задание|~задача]
            * (дальше/далее/следующ*/еще/~другой) *
            
        script:
            #генерация примера
            
            
    state: СледующийПример
        q: * (дальше/далее/следующ*/еще/~другой) *
        script:
            #вызывается state Пример
