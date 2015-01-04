#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_25")
    if window(''):
        click('127.0.0.1')
        click('..._2')

        if window('Open'):
            select('JFileChooser_0', 'C:/bla')
        close()

        select('Web root directory', 'C:/bla')
    close()

    pass
