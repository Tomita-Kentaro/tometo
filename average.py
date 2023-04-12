import gspread
from oauth2client.service_account import ServiceAccountCredentials
from itertools import count

scope =['https://spreadsheets.google.com/feeds', 'https://www.googleapis.com/auth/drive']
creds = ServiceAccountCredentials.from_json_keyfile_name('./client_secret.json', scope)
client = gspread.authorize(creds)

sheet = client.open("成績リスト").sheet1

sheet.update_cell(1, 6, "平均点")
for col in count():
    if not sheet.cell(2+col, 1).value:
        break
    sum_sheet = 0
    for row in range(4):
        sum_sheet += int(sheet.cell(2+col, 2+row).value)
    sheet.update_cell(2+col, 6, sum_sheet/4)