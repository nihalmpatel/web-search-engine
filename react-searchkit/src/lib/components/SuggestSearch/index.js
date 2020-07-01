/*
 * This file is part of React-SearchKit.
 * Copyright (C) 2018 CERN.
 *
 * React-SearchKit is free software; you can redistribute it and/or modify it
 * under the terms of the MIT License; see LICENSE file for more details.
 */

import { connect } from '../../store';
import SuggestSearchComponent from './SuggestSearch';

export const SuggestSearch = connect(state => ({
  loading: state.results.loading,
  suggestedSearch: state.results.data.suggestedSearch,
}))(SuggestSearchComponent);
