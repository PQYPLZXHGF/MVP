package net.myerichsen.hremvp.dialogs;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.dbmodels.SexTypes;
import net.myerichsen.hremvp.person.providers.SexTypeProvider;

/**
 * Display all sex types
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. jan. 2019
 *
 */
public class SexTypeNavigatorDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	IEclipseContext context;
	private IEventBroker eventBroker;

	private SexTypeProvider provider;
	private Table table;
	private int SexesPid;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 * @param context
	 */
	public SexTypeNavigatorDialog(Shell parentShell, IEclipseContext context) {
		super(parentShell);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
		provider = new SexTypeProvider();
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Select a Sex");
		setTitle("Sexes");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.grabExcessHorizontalSpace = false;
		container.setLayoutData(gd_container);

		TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = table.getSelection();
				TableItem selectedItem = items[0];
				setSexesPid(Integer.parseInt(selectedItem.getText(0)));
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnHistoricalSex = tableViewerColumn_1.getColumn();
		tblclmnHistoricalSex.setWidth(100);
		tblclmnHistoricalSex.setText("Sex");

		try {
			List<SexTypes> sexTypeList = provider.get();
			table.removeAll();

			for (int i = 0; i < sexTypeList.size(); i++) {
				SexTypes type = sexTypeList.get(i);
				TableItem item = new TableItem(table, SWT.NONE);
				SexesPid = type.getSexTypePid();
				item.setText(0, Integer.toString(SexesPid));
				item.setText(1, type.getLabel());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}

		return area;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(477, 464);
	}

	/**
	 * @return the sexesPid
	 */
	public int getSexesPid() {
		return SexesPid;
	}

	/**
	 * @param sexesPid the sexesPid to set
	 */
	public void setSexesPid(int sexesPid) {
		SexesPid = sexesPid;
	}

}