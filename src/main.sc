require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
# Подключение javascript обработчиков
require: js/getters.js
require: js/reply.js
require: js/actions.js

# Подключение сценарных файлов
require: sc/addNote.sc
require: sc/doNote.sc
require: sc/deleteNote.sc
require: sc/noteDone.sc

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
        
    state: КоличествоОпераций
        a: ммм
        
    state: ДиапазонПримера
        a: ммм
        
    state: ДиапазонОтвета
        a: ммм
        
        
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
            
        a: $AnyText::anyText
            
    state: СледующийПример
        q: * (дальше/далее/следующ*/еще/~другой) *
        script:
            #вызывается state Пример
