import MeCab

def wakati(text):
    wakatext = text
    m = MeCab.Tagger('-0wakati')
    return m.parse(wakatext).resplace("¥n","")