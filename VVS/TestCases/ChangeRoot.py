#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("1.8.0_25")
    if window(''):
        assert_p('Start server', 'Text', 'Start server')
        assert_p('lbl:./TestSite', 'Text', './TestSite')
        click('...')

        if window('Open'):
            select('JFileChooser_0', '#H/Desktop')
        close()

        click('SubmitConfig')
        assert_p('lbl:/Users/salexandru/Desktop', 'Text', '/Users/salexandru/Desktop')
        assert_p('JPanel_3', 'Enabled', 'true')
        select('Web root directory', '/Users/andru/Desktop')
        click('SubmitConfig')

        if window('Configuration'):
            assert_p('JPanel_3', 'Enabled', 'true')
            assert_p('JOptionPane_0', 'Enabled', 'true')
            click('OK')
        close()

        click('Start server')
        click('...')

        if window('Open'):
            select('JFileChooser_0', '#H/Downloads')
        close()

        assert_p('lbl:/Users/salexandru/Desktop', 'Text', '/Users/salexandru/Desktop')
        click('SubmitConfig')
        assert_p('lbl:/Users/salexandru/Downloads', 'Text', '/Users/salexandru/Downloads')
    close()

    pass