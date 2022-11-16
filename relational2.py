import mariadb
import sys
from lxml import etree

# Connect to MariaDB Platform
try:
    conn = mariadb.connect(
        user="peanut222link",
        password="password",
        host="localhost",
        port=3306,
        database="databaseDIM"

    )
except mariadb.Error as e:
    print(f"Error connecting to MariaDB Platform: {e}")
    sys.exit(1)

# Get Cursor
cur = conn.cursor()
  
def parseXML(xmlfile):
    
    print(type(xmlfile))

    parser = etree.XMLPullParser(dtd_validation = False)

    context = etree.iterparse(xmlfile, dtd_validation = True )
    
    for action, elem in context:
        if (elem.tag == "journal"):
            print("%s: %s" % (elem.tag, elem.text))

    print(type(context))


def main():

    # parse xml file
    dblp = parseXML("dblp.xml")
      
if __name__ == "__main__":
  
    # calling main function
    main()