#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("1.8.0_25")
    if window(''):
        assert_p('Start server', 'Text', 'Start server')
        assert_p('lbl:Maintenance Dir', 'Text', 'Maintenance Dir:')
        assert_p('lbl:./TestSite/maintenance', 'Text', './TestSite/maintenance')
        click('..._2')

        if window('Open'):
            select('JFileChooser_0', '#H/Music')
        close()

        assert_p('lbl:./TestSite/maintenance', 'Text', './TestSite/maintenance')
        click('SubmitConfig')
        assert_p('lbl:/Users/salexandru/Music', 'Text', '/Users/salexandru/Music')
        select('Maintenance directory', '/Users/lexanu/Music')
        click('SubmitConfig')

        if window('Configuration'):
            assert_p('JPanel_3', 'Enabled', 'true')
            assert_p('JPanel_2', 'Enabled', 'true')
            assert_p('JOptionPane_0', 'Enabled', 'true')
            click('OK')
        close()
    close()

    pass