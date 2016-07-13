/*
 * Copyright 2016 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mmarquee.automation.controls;

import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.AutomationElement;
import mmarquee.automation.ControlType;
import mmarquee.automation.ElementNotFoundException;
import mmarquee.automation.controls.rebar.AutomationReBar;
import mmarquee.automation.controls.ribbon.AutomationRibbonBar;
import mmarquee.automation.uiautomation.TreeScope;

import java.util.List;

/**
 * Created by inpwt on 28/01/2016.
 *
 * Encapsulates the functionality of 'containers' i.e. elemenst that
 * host other elements
 */
public class AutomationContainer extends AutomationBase {

    /**
     * Constructor for AutomationContainer
     *
     * @param element The underlying element
     */
    public AutomationContainer(AutomationElement element) {
        super(element);
    }

    protected AutomationElement getControlByControlType(int index, int id) {
        PointerByReference condition = this.automation.CreateControlTypeCondition(id);

        List<AutomationElement> collection = this.findAll(
                new TreeScope(TreeScope.TreeScope_Descendants), condition.getValue());

        return collection.get(index);
    }

    /**
     * Gets the control by the control type, for s given control index
     * @param index Index of the control
     * @param id Control type
     * @param controlName The control name to use
     * @return The matching element
     */
    protected AutomationElement getControlByControlType(int index, int id, String controlName) {
        List<AutomationElement> collection;

        AutomationElement foundElement = null;

        collection = this.findAll(new TreeScope(TreeScope.TreeScope_Descendants));

        int counter = 0;

        for (AutomationElement element : collection) {
            String cName = element.currentClassName();

            if (cName.equals(controlName)) {
                if (counter == index) {
                    foundElement = element;
                    break;
                } else {
                    counter++;
                }
            }
        }

        return foundElement;
    }

    /**
     * Gets the control by the control type, for s given control index.
     *
     * There is probably a much better way of doing this
     *
     * @param name Name of the control
     * @param id Control type
     * @param controlName The control name to use
     * @return The matching element
     * @throws ElementNotFoundException Did not find the element
     */
    protected AutomationElement getControlByControlType(String name, int id, String controlName) throws ElementNotFoundException {
        List<AutomationElement> collection;

        AutomationElement foundElement = null;

        collection = this.findAll(new TreeScope(TreeScope.TreeScope_Descendants));

        int length = collection.size();

        for (AutomationElement element : collection) {
            int retVal = element.currentControlType();
            String className = element.currentClassName();

            if (retVal == id) {
                if (className.equals(controlName)) {
                    String cName = element.getName();

                    if (cName.equals(name)) {
                        foundElement = element;
                        break;
                    }
                }
            }
        }

        if (foundElement == null) {
            throw new ElementNotFoundException();
        }

        return foundElement;
    }

    /**
     * Gets the control by the control type
     * @param name Name to use
     * @param id Control type
     * @return The matching element
     * @throws ElementNotFoundException Did not find the element
     */
    protected AutomationElement getControlByControlType(String name, int id) throws ElementNotFoundException {
        return this.findFirst(new TreeScope(TreeScope.TreeScope_Descendants),
                this.createAndCondition(
                        this.createNamePropertyCondition(name).getValue(),
                        this.createControlTypeCondition(id).getValue()));
    }

    /**
     * Gets the control by the control type and automation ID
     * @param automationId Name to use
     * @param controlType Control type
     * @return The matching element
     * @throws ElementNotFoundException Did not find the element
     */
    protected AutomationElement getControlByAutomationId(String automationId, int controlType) throws ElementNotFoundException {
        return this.findFirst(new TreeScope(TreeScope.TreeScope_Descendants),
                this.createAndCondition(
                        this.createAutomationIdPropertyCondition(automationId).getValue(),
                        this.createControlTypeCondition(controlType).getValue()));
    }

    /**
     * Gets the checkbox associated with the given index
     * @param index Index of the control
     * @return AutomationCheckbox that represents the found control
     */
    public AutomationCheckbox getCheckbox(int index) {
        return new AutomationCheckbox(this.getControlByControlType(index, ControlType.CheckBox));
    }

    /**
     * Gets the Tab control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationTab getTab(int index) {
        return new AutomationTab(this.getControlByControlType(index, ControlType.Tab));
    }

    /**
     * Gets the Editbox control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationEditBox getEditBox(int index) {
        return new AutomationEditBox(this.getControlByControlType(index, ControlType.Edit));
    }

    /**
     * Gets the EditBox (with password marking) associated with the given index
     * @param index The index
     * @return The found control
     */
    public AutomationEditBox getPasswordEditBox(int index) {
        return new AutomationEditBox(this.getControlByControlType(index, ControlType.Edit, "PasswordBox"));
    }

    /**
     * Gets the ProgressBar control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationProgressBar getProgressBar(int index) {
        return new AutomationProgressBar(this.getControlByControlType(index, ControlType.ProgressBar));
    }

    /**
     * Gets the ProgressBar control associated with the given index
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationProgressBar getProgressBar(String name) throws ElementNotFoundException {
        return new AutomationProgressBar(this.getControlByControlType(name, ControlType.ProgressBar));
    }

    /**
     * Gets the Editbox control associated with the given name
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationEditBox getEditBox(String name) throws ElementNotFoundException {
        return new AutomationEditBox(this.getControlByControlType(name, ControlType.Edit));
    }

    /**
     * Gets the slider control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationSlider getSlider(int index) {
        return new AutomationSlider(this.getControlByControlType(index, ControlType.Slider));
    }

    /**
     * Gets the slider control associated with the given index
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationSlider getSlider(String name) throws ElementNotFoundException {
        return new AutomationSlider(this.getControlByControlType(name, ControlType.Slider));
    }

    /**
     * Gets the (JHC) Masked Edit control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationMaskedEdit getMaskedEdit(int index) {
        return new AutomationMaskedEdit(this.getControlByControlType(index, ControlType.Edit, "TAutomationMaskEdit"));
    }

    /**
     * Gets the (JHC) Masked Edit control associated with the given index
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationMaskedEdit getMaskedEdit(String name) throws ElementNotFoundException {
        return new AutomationMaskedEdit(this.getControlByControlType(name, ControlType.Edit, "TAutomatedMaskEdit"));
    }

    /**
     * Gets the radio button control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationRadioButton getRadioButton(int index) {
        return new AutomationRadioButton(this.getControlByControlType(index, ControlType.RadioButton));
    }

    /**
     * Gets the text box control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationTextBox getTextBox(int index) {
        return new AutomationTextBox(this.getControlByControlType(index, ControlType.Text));
    }

    /**
     * Gets the combobox control associated with the given name
     * @param index Index of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationComboBox getCombobox(int index) throws ElementNotFoundException {
        return new AutomationComboBox(this.getControlByControlType(index, ControlType.ComboBox));
    }

    /**
     * Gets the combobox control associated with the given name
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationComboBox getCombobox(String name) throws ElementNotFoundException {
        return new AutomationComboBox(this.getControlByControlType(name, ControlType.ComboBox));
    }

    /**
     * Gets the button control associated with the given name
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationButton getButton(String name) throws ElementNotFoundException {
        return new AutomationButton(this.getControlByControlType(name, ControlType.Button));
    }

    /**
     * Gets the button using the automation ID
     * @param id The automation id
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationButton getButtonByAutomationId(String id)  throws ElementNotFoundException {
        return new AutomationButton(this.getControlByAutomationId(id, ControlType.Button));
    }


    /**
     * Gets the button control associated with the given index
     * @param index The index of the button
     * @return The AutomationButton
     */
    public AutomationButton getButton(int index) {
        return new AutomationButton(this.getControlByControlType(index, ControlType.Button));
    }

    /**
     * Gets the  String Grid control associated with the given index, with a specific control name
     * @param index Index of the control
     * @param controlName*
     * @return The found control
     */
    public AutomationDataGrid getDataGrid(int index, String controlName) {
        return new AutomationDataGrid(this.getControlByControlType(index, ControlType.DataGrid, controlName));
    }

    /**
     * Gets the String Grid control associated with the given index
     * @param index The index to look for
     * @return The string grid
     */
    public AutomationDataGrid getDataGrid(int index) {
        return new AutomationDataGrid(this.getControlByControlType(index, ControlType.DataGrid));
    }

    /**
     * Gets the document associated with the given index
     * @param index Index of the control
     * @return The document control
     */
    public AutomationDocument getDocument(int index) {
        return new AutomationDocument(this.getControlByControlType(index, ControlType.Document));
    }

    /**
     * Gets the hyperlink control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationHyperlink getHyperlink(int index) {
        return new AutomationHyperlink(this.getControlByControlType(index, ControlType.Hyperlink));
    }

    /**
     * Gets the treeview control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationTreeView getTreeView(int index) {
        return new AutomationTreeView(this.getControlByControlType(index, ControlType.Tree));
    }

    /**
     * Gets the treeview control associated with the given name
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationTreeView getTreeView(String name) throws ElementNotFoundException {
        return new AutomationTreeView(this.getControlByControlType(name, ControlType.Tree));
    }

    /**
     * Gets the list control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationList getListItem(int index) {
        return new AutomationList(this.getControlByControlType(index, ControlType.List));
    }

    /**
     * Gets the calendar control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationCalendar getCalendar(int index) {
        return new AutomationCalendar(this.getControlByControlType(index, ControlType.Calendar));
    }

    /**
     * Gets the panel control associated with the given index
     * @param index Index of the control
     * @return The found control
     */
    public AutomationPanel getPanel(int index) {
        return new AutomationPanel(this.getControlByControlType(index, ControlType.Pane));
    }

    /**
     * Gets the panel control associated with the given name
     * @param name Name of the control
     * @return The found control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationPanel getPanel(String name) throws ElementNotFoundException {
        return new AutomationPanel(this.getControlByControlType(name, ControlType.Pane));
    }

    /**
     * Get the AppBar associated with the given index
     * @param index The index
     * @return The AutomationAppBar
     */
    public AutomationAppBar getAppBar(int index) {
        return new AutomationAppBar(this.getControlByControlType(index, ControlType.AppBar));
    }

    /**
     * Get the ToolBar associated with the given name
     * @param name The name
     * @return The AutomationToolBar
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationToolBar getToolBar(String name) throws ElementNotFoundException {
        return new AutomationToolBar(this.getControlByControlType(name, ControlType.ToolBar));
    }

    /**
     * Get the ToolBar associated with the given index
     * @param index The index
     * @return The AutomationToolBar
     */
    public AutomationToolBar getToolBar(int index) {
        return new AutomationToolBar(this.getControlByControlType(index, ControlType.ToolBar));
    }

    /**
     * Get the RibbonBar associated this container
     * @return The AutomationRibbonBar
     */
    public AutomationRibbonBar getRibbonBar() {
        return new AutomationRibbonBar(this.getControlByControlType(0, ControlType.Pane, "UIRibbonCommandBarDock"));
    }

    /**
     * Gets the AutomationReBar associated with this index
     * @param index The index
     * @return The control wrapper
     */
    public AutomationReBar getReBar(int index) {
        return new AutomationReBar(this.getControlByControlType(index, ControlType.Pane, "ReBarWindow32"));
    }

    /**
     * Get the AutomationSplitButton associated with the given name
     * @param name The name to look for
     * @return The AutomationSplitButton
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationSplitButton getSplitButton(String name) throws ElementNotFoundException {
        return new AutomationSplitButton(this.getControlByControlType(name, ControlType.SplitButton));
    }

    /**
     * Get the AutomationImage associated with the given name
     * @param name The name to look for
     * @return The AutomationImage
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationImage getImage(String name) throws ElementNotFoundException {
        return new AutomationImage(this.getControlByControlType(name, ControlType.Image));
    }

    /**
     * Get the AutomationSpinner element associated with the given name
     * @param name The name to look for
     * @return The AutomationSpinner control
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationSpinner getSpinner(String name) throws ElementNotFoundException {
        return new AutomationSpinner(this.getControlByControlType(name, ControlType.Spinner));
    }

    /**
     * Get the AutomationCustom element associated with the given name
     * @param name The name to look for
     * @return The AutomationCustom
     * @throws ElementNotFoundException Did not find the element
     */
    public AutomationCustom getCustom(String name) throws ElementNotFoundException {
        return new AutomationCustom(this.getControlByControlType(name, ControlType.Custom));
    }

    /**
     * Dumps the control tree, used for exploring interfaces
     */
    public void dumpUI() {
        logger.info("About to start dumping");

        List<AutomationElement> collection = this.findAll(new TreeScope(TreeScope.TreeScope_Descendants));

        for (AutomationElement element : collection) {
            String cName = element.getName();

            logger.info(".." + cName);
        }

        logger.info("All done dumping");
    }
}