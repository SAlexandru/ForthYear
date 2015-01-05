#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("1.8.0_25")
    if window(''):
        assert_p('JPanel_1', 'Enabled', 'true')
        assert_p('WebServer control', 'Enabled', 'true')
        click('lbl:Server listening on port', 'Meta')
        assert_p('JPanel_3', 'Enabled', 'true')
        assert_p('Start server', 'Text', 'Start server')
        click('Start server')
        assert_p('WebServer control', 'Enabled', 'true')
        select('Server listening on port', '10008')
        click('SubmitConfig')
        assert_p('lbl:10008', 'Text', '10008')
        click('...')

        if window('Open'):
            select('JFileChooser_0', '#H/Library')
        close()

        assert_p('lbl:./TestSite', 'Text', './TestSite')
        click('SubmitConfig')
        assert_p('lbl:/Users/salexandru/Library', 'Text', '/Users/salexandru/Library')
        click('..._2')

        if window('Open'):
            select('JFileChooser_0', '#H/Music')
        close()

        assert_p('lbl:./TestSite/maintenance', 'Text', './TestSite/maintenance')
        click('SubmitConfig')
        click('SubmitConfig')
        assert_p('lbl:/Users/salexandru/Music', 'Text', '/Users/salexandru/Music')
        select('Web root directory', '/rs/salexandru/Library')
        click('SubmitConfig')

        if window('Configuration'):
            click('JOptionPane_0', 14, 26, 'Meta')
            assert_p('JPanel_3', 'Enabled', 'true')
            click('OK')
        close()

        click('...')

        if window('Open'):
            select('JFileChooser_0', '#H/Downloads')
        close()

        select('Maintenance directory', '/Users/andru/Music')
        click('SubmitConfig')

        if window('Configuration'):
            assert_p('JOptionPane_0', 'Enabled', 'true')
            click('OK')
        close()

        click('..._2')

        if window('Open'):
            select('JFileChooser_0', '#H/Pictures')
        close()

        select('Server listening on port', '10008tt10_12')
        click('SubmitConfig')

        if window('Configuration'):
            assert_p('JPanel_3', 'Enabled', 'true')
            assert_p('JOptionPane_0', 'Enabled', 'true')
            click('OK')
        close()

        click('Stop server')
        assert_p('Start server', 'Text', 'Start server')
        click('SubmitConfig')

        if window('Configuration'):
            assert_p('JPanel_2', 'Enabled', 'true')
            assert_p('JPanel_3', 'Enabled', 'true')
            click('OK')
        close()
    close()

    pass