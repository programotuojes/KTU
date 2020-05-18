import React, { useState } from 'react';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogActions from '@material-ui/core/DialogActions';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';

function Popup({ open, onClose, onDelete }) {
  const [loading, setLoading] = useState(false);

  function handleDelete() {
    setLoading(true);
    onDelete();

    setLoading(false);
    onClose();
  }

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogContent>
        <DialogContentText>Delete this item?</DialogContentText>
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose}>No</Button>
        <Button onClick={handleDelete} disabled={loading}>
          Yes
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default Popup;
