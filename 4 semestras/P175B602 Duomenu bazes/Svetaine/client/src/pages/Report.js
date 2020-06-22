import React from 'react';
import ReportFilters from '../components/ReportFilters';
import { useSelector } from 'react-redux';
import { getResults } from '../store/selectors/report';
import ReportResults from '../components/ReportResults';

function Report() {
  const results = useSelector(getResults);

  return <>{(results && <ReportResults results={results} />) || <ReportFilters />}</>;
}

export default Report;
